package com.github.olly.workshop.trafficgen.service.tranformation;

import com.github.olly.workshop.trafficgen.clients.ImageHolderClient;
import com.github.olly.workshop.trafficgen.clients.ImageOrchestratorClient;
import com.github.olly.workshop.trafficgen.model.Transformation;
import com.github.olly.workshop.trafficgen.model.TransformationRequest;
import com.github.olly.workshop.trafficgen.model.TransformationType;
import com.github.olly.workshop.trafficgen.service.TrafficGenMetricsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransformationService {

    private final TrafficGenMetricsService trafficGenMetricsService;
    private final ImageHolderClient imageHolderClient;
    private final ImageOrchestratorClient imageOrchestratorClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);

    private static final int MAX_NUMBER_OF_TRANSFORMATION_PER_IMAGE = 5;
    private static final int RANDOM_NAME_SUFFIX_LENGTH = 10;
    private static final float MIN_RESIZE_FACTOR = 0.01f;
    private static final int MAX_RESIZE_FACTOR = 3;
    private static final float PERSISTENCE_PROBABILITY = 1 / 100;

    private final Random random = new Random();

    @Async
    public void sendRandomRequest() {
        String randomImageId = "";
        try {
            randomImageId = imageHolderClient.getRandomImage().getHeaders().getFirst("imageId");
        } catch (Exception e) {
            LOGGER.warn("Unable to find images due to exception.", e);
            return;
        }
        TransformationRequest randomTransformationRequest = createRandomTransformationRequest(randomImageId);
        try {
            imageOrchestratorClient.transform(randomTransformationRequest);
            trafficGenMetricsService.transformationPerformed(true);
            LOGGER.info("Transformation of image " + randomImageId + " completed successfully");
        } catch (Exception e) {
            trafficGenMetricsService.transformationPerformed(false);
            LOGGER.warn("Tranformation of image " + randomImageId + " failed due to exception.", e);
        }
    }

    private Transformation randomTransformation() {
        TransformationType randomType = TransformationType.values()[random.nextInt(
                TransformationType.values().length)];

        Map<String, String> randomTransformationProperties = switch (randomType) {
            case rotate -> Map.of("degrees",
                    String.valueOf((random.nextBoolean() ? 1 : -1) * random.nextInt(360)));
            case flip -> Map.of(
                    "vertical", String.valueOf(random.nextBoolean()),
                    "horizontal", String.valueOf(random.nextBoolean()));
            case grayscale -> Map.of();
            case resize -> Map.of("factor",
                    String.valueOf(MIN_RESIZE_FACTOR * random.nextInt((int) (MAX_RESIZE_FACTOR / MIN_RESIZE_FACTOR))));
        };

        return new Transformation(randomType, randomTransformationProperties);
    }

    private TransformationRequest createRandomTransformationRequest(String imageId) {
        final Boolean persist = random.nextInt((int) (1 / PERSISTENCE_PROBABILITY)) == 0;
        final String name = imageId + RandomStringUtils.randomAlphanumeric(RANDOM_NAME_SUFFIX_LENGTH).toLowerCase();
        List<Transformation> transformations = random.ints(random.nextInt(
                        MAX_NUMBER_OF_TRANSFORMATION_PER_IMAGE)).mapToObj(i -> randomTransformation())
                .collect(Collectors.toList());

        return new TransformationRequest(imageId, transformations, persist, name);
    }
}
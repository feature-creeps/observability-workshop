package com.github.olly.workshop.trafficgen.service;

import com.github.olly.workshop.trafficgen.model.Transformation;
import com.github.olly.workshop.trafficgen.service.clients.*;
import com.github.olly.workshop.trafficgen.model.TransformationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RandomTransformationService {

    @Autowired
    private TrafficGenMetricsService trafficGenMetricsService;

    @Autowired
    private ImageHolderClient imageHolderClient;
    @Autowired
    private ImageOrchestratorClient imageOrchestratorClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomTransformationService.class);

    private static final int MAX_NUMBER_OF_TRANSFORMATION_PER_IMAGE = 5;
    private static final int RANDOM_NAME_LENGTH = 10;

    private Random random = new Random();

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

    private TransformationRequest createRandomTransformationRequest(String imageId) {
        final Boolean persist = random.nextBoolean();
        final String name = RandomStringUtils.randomAlphanumeric(RANDOM_NAME_LENGTH).toLowerCase();
        List<Transformation> transformations = random.ints(random.nextInt(
                MAX_NUMBER_OF_TRANSFORMATION_PER_IMAGE)).mapToObj(i -> Transformation.random())
                .collect(Collectors.toList());

        return new TransformationRequest(imageId, transformations, persist, name);
    }
}
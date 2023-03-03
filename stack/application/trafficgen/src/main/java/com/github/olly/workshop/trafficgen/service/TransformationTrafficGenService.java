package com.github.olly.workshop.trafficgen.service;

import com.github.olly.workshop.trafficgen.model.Image;
import com.github.olly.workshop.trafficgen.model.Transformation;
import com.github.olly.workshop.trafficgen.model.TransformationType;
import com.github.olly.workshop.trafficgen.service.clients.*;
import com.github.olly.workshop.trafficgen.model.TransformationRequest;
import com.github.olly.workshop.springevents.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TransformationTrafficGenService {

    @Autowired
    private ImageHolderClient imageHolderClient;
    @Autowired
    private ImageOrchestratorClient imageOrchestratorClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationTrafficGenService.class);

    private Random random = new Random();

    public void generateTraffic() {
        sendRandomRequest();
    }

    private void sendRandomRequest() {
        LOGGER.info("Getting random imageId");
        String randomImageId = imageHolderClient.getRandomImage().getHeaders().getFirst("imageId");
        LOGGER.info("Sending random TransformationRequest for image " + randomImageId);
        TransformationRequest randomTransformationRequest = createRandomTransformationRequest(randomImageId);
        imageOrchestratorClient.transform(randomTransformationRequest);
        LOGGER.info("Transformation completed");
    }

    private TransformationRequest createRandomTransformationRequest(String imageId) {
        final Boolean persist = random.nextBoolean();
        final String name = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        List<Transformation> transformations = random.ints(random.nextInt(5)).mapToObj(i -> Transformation.random())
                .collect(Collectors.toList());

        return new TransformationRequest(imageId, transformations, persist, name);
    }
}
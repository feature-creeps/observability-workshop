package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.config.LogTraceContextUtil;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.Transformation;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageFlipClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageGrayscaleClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageResizeClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageRotatorClient;
import com.github.olly.workshop.springevents.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TransformationService {

    private final ImageOrchestratorMetricsService imageOrchestratorMetricsService;
    private final EventService eventService;
    private final ImageGrayscaleClient imageGrayscaleClient;
    private final ImageRotatorClient imageRotatorClient;
    private final ImageResizeClient imageResizeClient;
    private final ImageFlipClient imageFlipClient;
    private final LogTraceContextUtil contextUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);


    public Image transform(Image image, List<Transformation> transformations) {

        List<String> collectTransformations = IntStream.range(0, transformations.size())
                .mapToObj(index -> transformations.get(index).getType().toString())
                .collect(Collectors.toList());
        this.eventService.addFieldToActiveEvent("transformations", collectTransformations);

        for (Transformation transformation : transformations) {
            contextUtil.put(transformation);
            this.eventService.addFieldToActiveEvent("content.type", image.getMimeType());
            this.eventService.addFieldToActiveEvent("transformation.properties", transformation.getProperties());
            switch (transformation.getType()) {
                case grayscale:
                    image = grayscale(image, transformation.getProperties());
                    imageOrchestratorMetricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case rotate:
                    image = rotate(image, transformation.getProperties());
                    imageOrchestratorMetricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case resize:
                    image = resize(image, transformation.getProperties());
                    imageOrchestratorMetricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case flip:
                    image = flip(image, transformation.getProperties());
                    imageOrchestratorMetricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                default:
                    this.eventService.addFieldToActiveEvent("transformation.unknown", transformation.getType().name());
                    LOGGER.warn("Skipping unrecognized transformation: {}", transformation.getType().name());
            }
        }
        return image;
    }

    private Image flip(Image image, Map<String, String> properties) {
        LOGGER.info("Performing flip transformation with properties {}", properties);
        return transformFlip(image, properties);
    }

    private Image resize(Image image, Map<String, String> properties) {
        LOGGER.info("Performing resize transformation with properties {}", properties);
        return transformResize(image, properties);
    }

    private Image rotate(Image image, Map<String, String> properties) {
        LOGGER.info("Performing rotate transformation with properties {}", properties);
        return transformRotate(image, properties);
    }

    private Image grayscale(Image image, Map<String, String> properties) {
        LOGGER.info("Performing grayscale transformation");
        return transformGrayscale(image);
    }

    private Image transformFlip(Image image, Map<String, String> properties) {
        Image transformed = imageFlipClient.transform(image,
                Boolean.parseBoolean(properties.get("vertical")),
                Boolean.parseBoolean(properties.get("horizontal")));
        LOGGER.info("Flipped image OK");
        return transformed;
    }

    private Image transformGrayscale(Image image) {
        Image transformed = imageGrayscaleClient.transform(image);
        LOGGER.info("Converted image to grayscale OK");
        return transformed;
    }

    private Image transformRotate(Image image, Map<String, String> properties) {
        Image transformed = imageRotatorClient.transform(image, properties.get("degrees"));
        LOGGER.info("Rotated image OK");
        return transformed;
    }

    private Image transformResize(Image image, Map<String, String> properties) {
        Image transformed = imageResizeClient.transform(image, properties.get("factor"));
        LOGGER.info("Resized image OK");
        return transformed;
    }
}

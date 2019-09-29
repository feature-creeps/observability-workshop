package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.config.LoggingContextUtil;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.Transformation;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageFlipClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageGrayscaleClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageResizeClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageRotatorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.honeycomb.beeline.tracing.Beeline;

import java.util.List;
import java.util.Map;

@Service
public class TransformationService {

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private BeelineService beeline;

    @Autowired
    private ImageGrayscaleClient imageGrayscaleClient;

    @Autowired
    private ImageRotatorClient imageRotatorClient;

    @Autowired
    private ImageResizeClient imageResizeClient;

    @Autowired
    private ImageFlipClient imageFlipClient;

    @Autowired
    private LoggingContextUtil lcu;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);


    public Image transform(Image image, List<Transformation> transformations) {

        for (Transformation transformation : transformations) {
            lcu.mdcPut(transformation);
            this.beeline.addFieldToActiveSpan("tranformation.content_type", image.getMimeType());
            this.beeline.addFieldToActiveSpan("tranformation.transformation", transformation);
            this.beeline.addFieldToActiveSpan("tranformation.properties", transformation.getProperties());
            switch (transformation.getType()) {
                case grayscale:
                    image = grayscale(image, transformation.getProperties());
                    this.beeline.addFieldToActiveSpan("tranformation.greyscale", true);
                    metricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case rotate:
                    image = rotate(image, transformation.getProperties());
                    this.beeline.addFieldToActiveSpan("tranformation.rotate", true);
                    metricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case resize:
                    image = resize(image, transformation.getProperties());
                    this.beeline.addFieldToActiveSpan("tranformation.resize", true);
                    metricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                case flip:
                    image = flip(image, transformation.getProperties());
                    this.beeline.addFieldToActiveSpan("tranformation.flip", true);
                    metricsService.transformationPerformed(image.getMimeType(), transformation.getType().name());
                    break;
                default:
                    this.beeline.addFieldToActiveSpan("tranformation.unknown", transformation.getType().name());
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
        Image transformed = imageFlipClient.transform(image, Boolean.valueOf(properties.get("vertical")), Boolean.valueOf(properties.get("horizontal")));
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

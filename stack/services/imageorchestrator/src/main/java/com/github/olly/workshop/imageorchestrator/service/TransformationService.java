package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.Transformation;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageGrayscaleClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageRotatorClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class TransformationService {

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private ImageGrayscaleClient imageGrayscaleClient;

    @Autowired
    private ImageRotatorClient imageRotatorClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);


    public Image transform(Image image, List<Transformation> transformations) {

        for (Transformation transformation : transformations) {
            switch (transformation.getType()) {
                case grayscale:
                    image = grayscale(image, transformation.getProperties());
                    metricsService.transformationPerformed(image.getMimeType(), "grayscale");
                    break;
                case rotate:
                    image = rotate(image, transformation.getProperties());
                    metricsService.transformationPerformed(image.getMimeType(), "rotate");
                    break;
                default:
                    LOGGER.warn("Skipping unrecognized transformation: {}", transformation.getType());
            }
        }
        return image;
    }

    private Image rotate(Image image, Map<String, String> properties) {
        LOGGER.info("Performing rotate transformation with properties {}", properties);
        return transformRotate(image, properties);
    }

    private Image grayscale(Image image, Map<String, String> properties) {
        LOGGER.info("Performing grayscale transformation");
        return transformGrayscale(image);
    }

    private Image transformGrayscale(Image image) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", image.getData(), ContentType.create(image.getMimeType()), "image");
        HttpEntity response = imageGrayscaleClient.transform(builder.build());
        try {
            return new Image(IOUtils.toByteArray(response.getContent()), response.getContentType().getValue());
        } catch (IOException e) {
            LOGGER.error("Failed to transform to grayscale");
            return image;
        }
    }

    private Image transformRotate(Image image, Map<String, String> properties) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", image.getData(), ContentType.create(image.getMimeType()), "image");
        builder.addTextBody("degrees", properties.get("degrees"));
        HttpEntity response = imageRotatorClient.transform(builder.build());
        try {
            return new Image(IOUtils.toByteArray(response.getContent()), response.getContentType().getValue());
        } catch (IOException e) {
            LOGGER.error("Failed to transform to grayscale");
            return image;
        }
    }
}

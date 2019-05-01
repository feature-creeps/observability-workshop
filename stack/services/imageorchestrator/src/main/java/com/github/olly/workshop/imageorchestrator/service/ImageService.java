package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageHolderClient;
import com.github.olly.workshop.imageorchestrator.service.clients.ImageHolderUploadClient;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    TransformationService transformationService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private ImageHolderClient imageHolderClient;

    @Autowired
    private ImageHolderUploadClient imageHolderUploadClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image transform(TransformationRequest transformationRequest) {

        // load the image from imageholder
        Image originalImage;
        try {
            originalImage = loadImage(transformationRequest.getImageId());
        } catch (Throwable ex) {
            LOGGER.error("Failed loading image with id " + transformationRequest.getImageId() + " from imageholder", ex);
            return null;
        }

        final Image transformedImage = transformationService.transform(originalImage, transformationRequest.getTransformations());

        metricsService.imageTransformed(transformedImage.getMimeType());

        if (BooleanUtils.isTrue(transformationRequest.getPersist())) {
            imageHolderUploadClient.upload(transformedImage);
        }

        return transformedImage;
    }

    private Image loadImage(String id) {
        ResponseEntity<byte[]> response = imageHolderClient.getImage(id);
        return new Image(response.getBody(), response.getHeaders().getContentType().toString());
    }
}

package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.config.LoggingContextUtil;
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
    private EventService eventService;

    @Autowired
    private ImageHolderClient imageHolderClient;

    @Autowired
    private ImageHolderUploadClient imageHolderUploadClient;

    @Autowired
    private LoggingContextUtil lcu;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image transform(TransformationRequest transformationRequest) {

        // load the image from imageholder
        Image originalImage;
        try {
            this.eventService.addFieldToActiveEvent("tranformation.image.id", transformationRequest.getImageId());
            originalImage = loadImage(transformationRequest.getImageId());
        } catch (Throwable ex) {
            LOGGER.error("Failed loading image with id " + transformationRequest.getImageId() + " from imageholder", ex);
            return null;
        }
        lcu.mdcPut(originalImage);

        final Image transformedImage = transformationService.transform(originalImage, transformationRequest.getTransformations());

        lcu.mdcPut(transformedImage);

        this.eventService.addFieldToActiveEvent("tranformation.image.content.type", transformedImage.getMimeType());
        metricsService.imageTransformed(transformedImage.getMimeType());

        if (BooleanUtils.isTrue(transformationRequest.getPersist())) {
            this.eventService.addFieldToActiveEvent("tranformation.image.persist", true);
            imageHolderUploadClient.upload(transformedImage, transformationRequest.getName());
        }

        return transformedImage;
    }

    private Image loadImage(String id) {
        ResponseEntity<byte[]> response = imageHolderClient.getImage(id);
        return new Image(response.getBody(), response.getHeaders().getContentType().toString());
    }
}

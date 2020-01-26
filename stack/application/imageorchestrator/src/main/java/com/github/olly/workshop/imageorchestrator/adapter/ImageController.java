package com.github.olly.workshop.imageorchestrator.adapter;

import com.github.olly.workshop.imageorchestrator.config.LogTraceContextUtil;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.service.EventService;
import com.github.olly.workshop.imageorchestrator.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private LogTraceContextUtil contextUtil;

    @Autowired
    private EventService eventService;

    @PostMapping(value = "transform")
    public ResponseEntity transform(@RequestBody TransformationRequest transformationRequest) {

        contextUtil.put(transformationRequest);
        LOGGER.info("Received new transformation request {}", transformationRequest);
        this.eventService.addFieldToActiveEvent("transformation.request", transformationRequest);
        this.eventService.addFieldToActiveEvent("action", "transform");

        if (StringUtils.isEmpty(transformationRequest.getImageId())) {
            LOGGER.error("Field imageId has to be set");
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "no_id");
            return new ResponseEntity<>("Field imageId has to be set", HttpStatus.BAD_REQUEST);
        }
        this.eventService.addFieldToActiveEvent("content.imageId", transformationRequest.getImageId());

        Image transformedImage = imageService.transform(transformationRequest);
        contextUtil.put(transformedImage);
        // return final image
        if (transformedImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(transformedImage.getMimeType()));
            headers.set("Image-ID", transformedImage.getId());
            headers.set("access-control-expose-headers", "Image-ID");
            this.eventService.addFieldToActiveEvent("content.transformed.type", MediaType.valueOf(transformedImage.getMimeType()));
            this.eventService.addFieldToActiveEvent("content.transformed.size", transformedImage.getSize());
            this.eventService.addFieldToActiveEvent("content.transformed.id", transformedImage.getId());
            this.eventService.addFieldToActiveEvent("action.transformed.success", true);
            LOGGER.info("Returning transformed image");
            return new ResponseEntity<>(transformedImage.getData(), headers, HttpStatus.OK);
        } else {
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "bad_request");
            LOGGER.error("Failed transforming image");
            return new ResponseEntity<>("Failed transforming image", HttpStatus.BAD_REQUEST);
        }
    }
}

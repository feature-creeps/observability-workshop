package com.github.olly.workshop.imageorchestrator.adapter;

import com.github.olly.workshop.imageorchestrator.config.LogTraceContextUtil;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.service.ImageService;
import com.github.olly.workshop.springevents.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
@RequestMapping(value = "/api/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;
    private final LogTraceContextUtil contextUtil;
    private final EventService eventService;

    @PostMapping(value = "transform")
    public ResponseEntity transform(@RequestBody TransformationRequest transformationRequest) {
        contextUtil.put(transformationRequest);
        this.eventService.addFieldToActiveEvent("action", "transform");
        LOGGER.info("Received new transformation request {}", transformationRequest);
        this.eventService.addFieldToActiveEvent("transformation.request", transformationRequest);

        if (!StringUtils.hasLength(transformationRequest.getImageId())) {
            LOGGER.error("Field imageId has to be set");
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "no_id");
            return new ResponseEntity<>("Field imageId has to be set", HttpStatus.BAD_REQUEST);
        }
        this.eventService.addFieldToActiveEvent("content.imageId", transformationRequest.getImageId());

        Image transformedImage = imageService.transform(transformationRequest);
        contextUtil.put(transformedImage);
        // return final image
        if (transformedImage != null && transformedImage.hasData()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(transformedImage.getMimeType()));
            headers.set("Image-ID", transformedImage.getId());
            headers.set("access-control-expose-headers", "Image-ID");
            this.eventService.addFieldToActiveEvent("content.transformed.type",
                    MediaType.valueOf(transformedImage.getMimeType()));
            this.eventService.addFieldToActiveEvent("content.transformed.size", transformedImage.getSize());
            this.eventService.addFieldToActiveEvent("content.transformed.id", transformedImage.getId());
            LOGGER.info("Returning transformed image");
            return new ResponseEntity<>(transformedImage.getData(), headers, HttpStatus.OK);
        } else {
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "bad_request");
            LOGGER.error("Failed transforming image");
            return new ResponseEntity<>("Failed transforming image", HttpStatus.BAD_REQUEST);
        }
    }
}

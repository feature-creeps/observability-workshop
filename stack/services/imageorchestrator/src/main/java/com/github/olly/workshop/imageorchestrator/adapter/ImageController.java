package com.github.olly.workshop.imageorchestrator.adapter;

import com.github.olly.workshop.imageorchestrator.config.LoggingContextUtil;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.service.ImageService;
import org.slf4j.MDC;
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
import io.honeycomb.beeline.tracing.Beeline;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private LoggingContextUtil lcu;

    @Autowired
    private Beeline beeline;

    @PostMapping(value = "transform")
    public ResponseEntity transform(@RequestBody TransformationRequest transformationRequest) {

        lcu.mdcPut(transformationRequest);
        LOGGER.info("Received new transformation request {}", transformationRequest);
        this.beeline.getActiveSpan().addField("transformation.request", transformationRequest);
        this.beeline.getActiveSpan().addField("action", "transform");

        if (StringUtils.isEmpty(transformationRequest.getImageId())) {
            LOGGER.error("Field imageId has to be set");
            this.beeline.getActiveSpan().addField("action.success", false);
            this.beeline.getActiveSpan().addField("action.failure_reason", "no_id");
            MDC.put("responseCode", String.valueOf(HttpStatus.BAD_REQUEST));
            return new ResponseEntity<>("Field imageId has to be set", HttpStatus.BAD_REQUEST);
        }
        this.beeline.getActiveSpan().addField("content.id", transformationRequest.getImageId());

        Image transformedImage = imageService.transform(transformationRequest);
        lcu.mdcPut(transformedImage);
        // return final image
        if (transformedImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(transformedImage.getMimeType()));
            this.beeline.getActiveSpan().addField("content.type", MediaType.valueOf(transformedImage.getMimeType()));
            this.beeline.getActiveSpan().addField("content.transformed_id", transformedImage.getId());
            this.beeline.getActiveSpan().addField("action.success", true);
            LOGGER.info("Returning transformed image");
            MDC.put("responseCode", String.valueOf(HttpStatus.OK));
            return new ResponseEntity<>(transformedImage.getData(), headers, HttpStatus.OK);
        } else {
            this.beeline.getActiveSpan().addField("action.success", false);
            this.beeline.getActiveSpan().addField("action.failure_reason", "bad_request");
            LOGGER.error("Failed transforming image");
            MDC.put("responseCode", String.valueOf(HttpStatus.BAD_REQUEST));
            return new ResponseEntity<>("Failed transforming image", HttpStatus.BAD_REQUEST);
        }
    }
}

package com.github.olly.workshop.imagegrayscale.controller;

import com.github.olly.workshop.imagegrayscale.service.EventService;
import com.github.olly.workshop.imagegrayscale.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/image")
public class ImageGrayscale {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageGrayscale.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private EventService beeline;

    @PostMapping(value = "/grayscale")
    public ResponseEntity toGrayscale(@RequestParam("image") MultipartFile image) {

        this.beeline.addFieldToActiveSpan("content.type", image.getContentType());
        this.beeline.addFieldToActiveSpan("action", "grayscale");
        MDC.put("mimeType", image.getContentType());
        LOGGER.info("Receiving {} image to convert to grayscale", image.getContentType());

        if (image.getContentType() != null &&
                !image.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", image.getContentType());
            this.beeline.addFieldToActiveSpan("action.success", false);
            this.beeline.addFieldToActiveSpan("action.failure_reason", "wrong_content_type");
            return new ResponseEntity<>("Wrong content type uploaded: " + image.getContentType(), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("Starting conversion");

        byte[] transformed = imageService.grayscale(image);

        if (transformed == null) {
            this.beeline.addFieldToActiveSpan("action.success", false);
            this.beeline.addFieldToActiveSpan("action.failure_reason", "internal_server_error");
            return new ResponseEntity<>("Failed to apply grayscale", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());


        LOGGER.info("Successfully converted image to grayscale");
        this.beeline.addFieldToActiveSpan("action.success", true);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType()))
                .headers(headers)
                .body(transformed);
    }

}

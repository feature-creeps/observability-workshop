package com.github.olly.workshop.imagetransformer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/transform")
public class ImageTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageTransformer.class);


    @PostMapping(value = "/grayscale")
    public ResponseEntity toGrayscale() {
        LOGGER.info("Request to convert an image to graysvcale");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}

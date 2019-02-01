package com.github.olly.workshop.imagegrayscale.controller;

import com.github.olly.workshop.imagegrayscale.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @PostMapping(value = "/grayscale")
    public ResponseEntity toGrayscale(@RequestParam("image") MultipartFile image) {
        LOGGER.info("Receiving {} image to convert to grayscale", image.getContentType());

        if (image.getContentType() != null &&
                !image.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", image.getContentType());
            return new ResponseEntity<>("Wrong content type uploaded: " + image.getContentType(), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("Starting conversion");


        byte[] transformed = imageService.grayscale(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());


        LOGGER.info("Successfully converted image to grayscale");
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType()))
                .headers(headers)
                .body(transformed);
    }

}

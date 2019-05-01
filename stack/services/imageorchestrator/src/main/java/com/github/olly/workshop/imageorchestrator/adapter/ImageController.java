package com.github.olly.workshop.imageorchestrator.adapter;

import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.service.ImageService;
import com.github.olly.workshop.imageorchestrator.service.MetricsService;
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

    @PostMapping(value = "transform")
    public ResponseEntity transform(@RequestBody TransformationRequest transformationRequest) {

        LOGGER.info("Received new transformation request {}", transformationRequest);

        if (StringUtils.isEmpty(transformationRequest.getImageId())) {
            LOGGER.error("Field imageId has to be set");
            return new ResponseEntity<>("Field imageId has to be set", HttpStatus.BAD_REQUEST);
        }

        Image transformedImage = imageService.transform(transformationRequest);

        // return final image
        if (transformedImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(transformedImage.getMimeType()));
            LOGGER.info("Returning transformed image");
            return new ResponseEntity<>(transformedImage.getData(), headers, HttpStatus.OK);
        } else {
            LOGGER.error("Failed transforming image");
            return new ResponseEntity<>("Failed transforming image", HttpStatus.BAD_REQUEST);
        }
    }
}

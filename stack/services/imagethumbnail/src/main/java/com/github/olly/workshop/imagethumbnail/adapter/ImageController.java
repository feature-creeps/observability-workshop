package com.github.olly.workshop.imagethumbnail.adapter;

import com.github.olly.workshop.imagethumbnail.config.LoggingContextUtil;
import com.github.olly.workshop.imagethumbnail.model.Image;
import com.github.olly.workshop.imagethumbnail.service.EventService;
import com.github.olly.workshop.imagethumbnail.service.ImageService;
import com.github.olly.workshop.imagethumbnail.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private LoggingContextUtil loggingContextUtil;

    @Autowired
    private EventService beeline;

    @GetMapping(value = "/{id}")
    public ResponseEntity getImage(@PathVariable("id") String id) {
        Image image = imageService.thumbnail(id);
        this.beeline.addFieldToActiveSpan("content.id", id);
        loggingContextUtil.mdcPut(image.getContentType());

        if (image == null) {
            LOGGER.error("Image with id {} not found", id);
            this.beeline.addFieldToActiveSpan("action.success", false);
            this.beeline.addFieldToActiveSpan("action.failure_reason", "image_not_found");
            throw new NotFoundException("Image not found");
        }

        LOGGER.info("Returning thumbnail from image with id {}", id);
        metricsService.imageThumbnailed(image.getContentType());
        this.beeline.addFieldToActiveSpan("content.type", image.getContentType());
        this.beeline.addFieldToActiveSpan("action.success", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getContentType()));

        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteImageFromCache(@PathVariable("id") String id) {
        imageService.dropFromCache(id);
        this.beeline.addFieldToActiveSpan("action", "delete_from_cache");
        this.beeline.addFieldToActiveSpan("action.success", false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

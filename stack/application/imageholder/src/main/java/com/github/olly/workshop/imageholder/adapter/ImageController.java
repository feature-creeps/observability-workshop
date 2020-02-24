package com.github.olly.workshop.imageholder.adapter;

import com.github.olly.workshop.imageholder.config.LoggingContextUtil;
import com.github.olly.workshop.imageholder.model.Image;
import com.github.olly.workshop.imageholder.service.EventService;
import com.github.olly.workshop.imageholder.service.ImageService;
import com.github.olly.workshop.imageholder.service.MetricsService;
import com.github.olly.workshop.imageholder.service.client.ImageThumbnailClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private ImageThumbnailClient imageThumbnailClient;

    @Autowired
    private LoggingContextUtil loggingContextUtil;

    @Autowired
    private EventService eventService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
        LOGGER.info("Returning all images");
        Collection<Image> all_images = imageService.getAllImagesLight();
        this.eventService.addFieldToActiveEvent("action", "get_all");
        this.eventService.addFieldToActiveEvent("image_count", all_images.size());
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(all_images, HttpStatus.OK);
    }

    @GetMapping(value = "/throw")
    public void ex() {
        LOGGER.info("Throwing an Exception now :)");
        throw new RuntimeException("woohooo");
    }

    @GetMapping(value = "/random")
    public ResponseEntity getRandomImage() {
        Image image = imageService.getRandomImage();
        loggingContextUtil.mdcPut(image);

        this.eventService.addFieldToActiveEvent("action", "random");

        if (image == null) {
            LOGGER.warn("No images in database!");
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "no_images_found");
            return new ResponseEntity<>("No images in database!", HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Returning random image with id {}", image.getId());
        this.eventService.addFieldToActiveEvent("content.imageId", image.getId());
        this.eventService.addFieldToActiveEvent("content.type", image.getContentType());
        this.eventService.addFieldToActiveEvent("content.size", image.getSize());

        metricsService.imageViewed(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getContentType()));
        headers.set("imageId", image.getId());

        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }

    // hack, cause I dont want to use JS
    @GetMapping(value = "/image")
    public ResponseEntity getImageByURLParam(@RequestParam("id") String id) {
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        this.eventService.addFieldToActiveEvent("action", "get");
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return getImage(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getImage(@PathVariable("id") String id) {
        Image image = imageService.getImageById(id);
        loggingContextUtil.mdcPut(image);
        this.eventService.addFieldToActiveEvent("action", "get");
        this.eventService.addFieldToActiveEvent("content.imageId", id);

        if (image == null) {
            LOGGER.error("Image with id {} not found", id);
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "image_not_found");
            throw new NotFoundException("Image not found");
        }

        this.eventService.addFieldToActiveEvent("content.type", image.getContentType());
        this.eventService.addFieldToActiveEvent("content.size", image.getSize());
        LOGGER.info("Returning image with id {}", id);
        metricsService.imageViewed(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getContentType()));

        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }

    // hack, cause html forms are limited to GET/POST
    @PostMapping(value = "/delete")
    public ResponseEntity deleteImageByURLParam(@RequestParam("id") String id) {
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        this.eventService.addFieldToActiveEvent("action", "delete");
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return deleteImage(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteImage(@PathVariable("id") String id) {
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        this.eventService.addFieldToActiveEvent("action", "delete");
        loggingContextUtil.mdcPut(imageService.getImageById(id));

        LOGGER.info("Deleting image with id {}", id);
        if (imageService.deleteImageById(id)) {
            try {
                imageThumbnailClient.informThumbnail(id);
            } catch (Exception ex) {
                this.eventService.addFieldToActiveEvent("app.error", 1);
                LOGGER.warn("Failed informing imagethumbnail service of image deletion", ex);
            }
            this.eventService.addFieldToActiveEvent("app.error", 0);
            return new ResponseEntity<>("deleted image with id " + id, HttpStatus.OK);
        } else {
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "image_not_found");
            throw new NotFoundException("Image with id " + id + " not found!");
        }
    }

    // so much hack
    @PostMapping(value = "/delete/all")
    public ResponseEntity deleteAllImages() {
        Collection<String> allImageIds = imageService.getAllImagesLight().stream().map(Image::getId).collect(Collectors.toList());
        LOGGER.info("Deleting all {} images", allImageIds.size());
        imageService.deleteAllImages();
        this.eventService.addFieldToActiveEvent("action", "delete_all");
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>("Deleted following images: " + allImageIds.toString(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile file, @RequestParam(value = "name", required = false) String name) throws IOException {

        MDC.put("mimeType", file.getContentType());
        this.eventService.addFieldToActiveEvent("action", "upload");

        if (file.getContentType() != null && !file.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", file.getContentType());
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "wrong_content_type");
            return new ResponseEntity<>("Wrong content type uploaded: " + file.getContentType(), HttpStatus.FORBIDDEN);
        }
        LOGGER.info("Receiving new image");
        Image image = new Image();
        image.setData(IOUtils.toByteArray(file.getInputStream()));
        image.setContentType(file.getContentType());

        MDC.put("imageSize", String.valueOf(file.getSize()));
        loggingContextUtil.mdcPut(image);

        if (name == null || name.isEmpty()) {
            image.setName("unknown_" + RandomStringUtils.randomNumeric(10));
        } else {
            image.setName(name);
        }
        image = imageService.save(image);

        loggingContextUtil.mdcPut(image);

        this.eventService.addFieldToActiveEvent("content.imageId", image.getId());
        this.eventService.addFieldToActiveEvent("content.name", name);
        this.eventService.addFieldToActiveEvent("content.type", file.getContentType());
        this.eventService.addFieldToActiveEvent("content.size", file.getSize());
        LOGGER.info("Save new image with id {} and name {}", image.getId(), name);
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(image.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/nameContaining/{fragment}")
    public ResponseEntity findWithNameContaining(@PathVariable("fragment") String fragment) {
        LOGGER.info("Finding all images with the name containing '{}'", fragment);
        this.eventService.addFieldToActiveEvent("action", "search");
        this.eventService.addFieldToActiveEvent("search.fragment", fragment);
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(imageService.findWithNamesContaining(fragment), HttpStatus.OK);
    }
}

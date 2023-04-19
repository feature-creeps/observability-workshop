package com.github.olly.workshop.imagerotator.adapter;

import com.github.olly.workshop.imagerotator.config.LoggingContextUtil;
import com.github.olly.workshop.imagerotator.service.ImageService;
import com.github.olly.workshop.springevents.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/image")
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private LoggingContextUtil lcu;

    @Autowired
    private EventService eventService;

    @PostMapping("rotate")
    public ResponseEntity rotateImage(@RequestParam("image") MultipartFile file,
            @RequestParam(value = "degrees") String degrees) throws IOException {

        lcu.mdcPut(file.getContentType(), degrees);
        this.eventService.addFieldToActiveEvent("action", "rotate");
        this.eventService.addFieldToActiveEvent("transformation.rotate.degrees", degrees);
        this.eventService.addFieldToActiveEvent("content.type", file.getContentType());
        this.eventService.addFieldToActiveEvent("content.size", file.getBytes().length);

        if (file.getContentType() == null ||
                !file.getContentType().startsWith("image/")) {
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "wrong_content_type");
            LOGGER.warn("Wrong content type uploaded: {}", file.getContentType());
            return new ResponseEntity<>("Wrong content type uploaded: " + file.getContentType(),
                    HttpStatus.BAD_REQUEST);
        }

        if (file.isEmpty()) {
            LOGGER.warn("Empty image uploaded");
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "empty_content");
            return new ResponseEntity<>("Empty image uploaded",
                    HttpStatus.BAD_REQUEST);
        }

        // ISSUE: we fail on floating point values
        int intDegrees = Integer.valueOf(degrees);
        LOGGER.info("Receiving {} image to rotate by {} degrees", file.getContentType(), intDegrees);

        byte[] rotatedImage = imageService.rotate(file, intDegrees);

        if (rotatedImage == null) {
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "internal_server_error");
            return new ResponseEntity<>("Failed to rotate image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        this.eventService.addFieldToActiveEvent("content.transformed.size", rotatedImage.length);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));

        LOGGER.info("Successfully rotated image");
        this.eventService.addFieldToActiveEvent("app.error", 0);
        return new ResponseEntity<>(rotatedImage, headers, HttpStatus.OK);
    }
}

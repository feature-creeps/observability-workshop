package com.github.olly.workshop.imageflip.adapter;

import com.github.olly.workshop.imageflip.config.LoggingContextUtil;
import com.github.olly.workshop.imageflip.service.EventService;
import com.github.olly.workshop.imageflip.service.ImageService;
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
    private EventService eventService;

    @Autowired
    private LoggingContextUtil lcu;

    @PostMapping("flip")
    public ResponseEntity flipImage(@RequestParam("image") MultipartFile file,
                                    @RequestParam(value = "vertical") Boolean vertical,
                                    @RequestParam(value = "horizontal") Boolean horizontal) throws IOException {
    
        this.eventService.addFieldToActiveEvent("content.type", file.getContentType());
        this.eventService.addFieldToActiveEvent("action", "flip");
        this.eventService.addFieldToActiveEvent("transformation.flip_vertical", vertical);
        this.eventService.addFieldToActiveEvent("transformation.flip_horizontal", horizontal);
        lcu.mdcPut(file.getContentType(), vertical, horizontal);

        if (file.getContentType() != null &&
                !file.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", file.getContentType());
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "wrong_content_type");
            return new ResponseEntity<>("Wrong content type uploaded: " + file.getContentType(), HttpStatus.BAD_REQUEST);
        }

        // ISSUE: we fail on floating point values
        LOGGER.info("Receiving {} image to flip.", file.getContentType());
        byte[] flippedImage = imageService.flip(file, vertical, horizontal);

        if (flippedImage == null) {
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "internal_server_error");
            return new ResponseEntity<>("Failed to flip image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));

        LOGGER.info("Successfully flipped image");
        this.eventService.addFieldToActiveEvent("action.success", true);
        return new ResponseEntity<>(flippedImage, headers, HttpStatus.OK);
    }
}

package com.github.olly.workshop.imageresize.adapter;

import com.github.olly.workshop.imageresize.config.LoggingContextUtil;
import com.github.olly.workshop.imageresize.service.EventService;
import com.github.olly.workshop.imageresize.service.ImageService;
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

    @PostMapping("resize")
    public ResponseEntity resizeImage(@RequestParam("image") MultipartFile file, @RequestParam(value = "factor") String factor) throws IOException {

        lcu.mdcPut(file.getContentType(), factor);
        this.eventService.addFieldToActiveEvent("content.type", file.getContentType());
        this.eventService.addFieldToActiveEvent("content.size.original", file.getBytes().length);
        this.eventService.addFieldToActiveEvent("action", "resize");
        this.eventService.addFieldToActiveEvent("transformation.resize_factor", factor);

        if (file.getContentType() != null &&
                !file.getContentType().startsWith("image/")) {
            LOGGER.warn("Wrong content type uploaded: {}", file.getContentType());
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "wrong_content_type");
            return new ResponseEntity<>("Wrong content type uploaded: " + file.getContentType(), HttpStatus.BAD_REQUEST);
        }

        // ISSUE: we fail on floating point values
        Double intFactor = Double.valueOf(factor);
        LOGGER.info("Receiving {} image to resize by {} factor", file.getContentType(), intFactor);

        byte[] resizedImage = imageService.resize(file, intFactor);
        this.eventService.addFieldToActiveEvent("content.size.new", resizedImage.length);
        this.eventService.addFieldToActiveEvent("content.size", resizedImage.length);

        if (resizedImage == null) {
            this.eventService.addFieldToActiveEvent("action.success", false);
            this.eventService.addFieldToActiveEvent("action.failure_reason", "internal_server_error");
            return new ResponseEntity<>("Failed to resize image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(file.getContentType()));

        LOGGER.info("Successfully resized image");
        this.eventService.addFieldToActiveEvent("action.success", true);
        return new ResponseEntity<>(resizedImage, headers, HttpStatus.OK);
    }
}

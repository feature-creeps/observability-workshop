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

    @Autowired
    private MetricsService metricsService;


    // rest controller only receiving a json object in the right format and returning it
    // format:
    //
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

//    @PostMapping("transform")
//    public ResponseEntity transformImage(@RequestBody TransformationRequest transformationRequest) throws IOException {
//
//        LOGGER.info("Receiving new image transformation request for id {}", id);
//
//        // todo get image
//        byte[] imageData = imageHolderClient.getImage(id);
//
//
//        String imageType = "";
//
//
//        Set<ImageTransformation> transformations = new HashSet<>();
//
//        if (grayscale != null) {
//            ImageTransformation grayscaleTransformation = new ImageTransformation("imagegrayscale", null);
//            transformations.add(grayscaleTransformation);
//        }
//
//        if (resize != null) {
//            ImageTransformation resizeTransformation = new ImageTransformation("imageresize",
//                    new ActionsBuilder()
//                            .add("x", Integer.valueOf(x))
//                            .add("y", Integer.valueOf(y)).build());
//            // todo impl
//            // transformations.add(resizeTransformation);
//        }
//
//        if (rotate != null) {
//            ImageTransformation rotateTransformation = new ImageTransformation("imagerotator",
//                    new ActionsBuilder()
//                            .add("degrees", Integer.valueOf(degrees)).build());
//            transformations.add(rotateTransformation);
//        }
//
//        imageService.orchestrate(new ImageOrchestration(transformations, imgBase64, imageData, imageType));
//
//
//        if (persist != null) {
//            LOGGER.info("Save transformed image with name {}", name);
//            // todo post to imageholder
//        }
//
//        return new ResponseEntity<>("Uploaded image with id " + id, HttpStatus.CREATED);
//    }
}

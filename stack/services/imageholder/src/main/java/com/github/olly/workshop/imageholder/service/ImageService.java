package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetricsService metricsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image save(Image image) {
        image.setId(RandomStringUtils.randomAlphanumeric(20).toLowerCase());
        Image save = imageRepository.save(image);
        metricsService.imageUploaded(image);
        return save;
    }


    public Collection<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Collection<Image> findWithNamesContaining(String fragment) {
        return imageRepository.findByNameContaining(fragment);
    }


    public Image getImageById(String id) {
        return imageRepository.findById(id).orElse(null);
    }


    public boolean deleteImageById(String id) {
        Image image = getImageById(id);
        if (image != null) {
            imageRepository.deleteById(id);
            metricsService.imageDeleted(image);
            LOGGER.info("Successfully deleted image with id {}.", id);
            return true;
        } else {
            LOGGER.info("Failed to delete image with id {}. Not found.", id);
            return false;
        }
    }

    public Image getRandomImage() {
        Collection<Image> allImages = getAllImages();

        return allImages.stream()
                .skip((int) (allImages.size() * Math.random()))
                .findFirst().orElse(null);
    }
}

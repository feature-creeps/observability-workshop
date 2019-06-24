package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.honeycomb.beeline.tracing.Beeline;

import java.util.Collection;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private Beeline beeline;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image save(Image image) {
        image.setId(RandomStringUtils.randomAlphanumeric(20).toLowerCase());
        this.beeline.getActiveSpan().addField("image", image.getId());
        Image save = imageRepository.save(image);
        metricsService.imageUploaded(image);
        return save;
    }


    public Collection<Image> getAllImages() {
        Collection<Image> all_images = imageRepository.findAll();
        this.beeline.getActiveSpan().addField("content.count", all_images.size());
        return all_images;
    }

    public Collection<Image> getAllImagesLight() {
        Collection<Image> all_image_ids = imageRepository.findAllIds();
        this.beeline.getActiveSpan().addField("content.count", all_image_ids.size());
        return all_image_ids;
    }

    public Collection<Image> findWithNamesContaining(String fragment) {
        this.beeline.getActiveSpan().addField("search.fragment", fragment);
        return imageRepository.findByNameContaining(fragment);
    }


    public Image getImageById(String id) {
        this.beeline.getActiveSpan().addField("content.id",id);
        return imageRepository.findById(id).orElse(null);
    }


    public boolean deleteImageById(String id) {
        this.beeline.getActiveSpan().addField("content.id", id);
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
        Collection<Image> allImages = getAllImagesLight();

        final Image image = allImages.stream()
                .skip((int) (allImages.size() * Math.random()))
                .findFirst().orElse(null);

        this.beeline.getActiveSpan().addField("content.random.id", image.getId());
        return image != null ? getImageById(image.getId()) : null;
    }
}

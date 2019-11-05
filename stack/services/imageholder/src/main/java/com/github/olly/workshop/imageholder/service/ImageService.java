package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

@Service
public class ImageService {

    private static int MAX_IMAGES_TO_KEEP = 1000;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image save(Image image) {
        checkForImageLimit();
        image.setId(RandomStringUtils.randomAlphanumeric(20).toLowerCase());
        this.eventService.addFieldToActiveEvent("imageId", image.getId());
        Image save = imageRepository.save(image);
        metricsService.imageUploaded(image);
        return save;
    }

    private void checkForImageLimit() {
        Collection<Image> allImagesLight = getAllImagesLight();
        while (allImagesLight.size() >= MAX_IMAGES_TO_KEEP) {
            LOGGER.warn("Too many images in db: {}/{}. Deleting a random image..", allImagesLight.size(), MAX_IMAGES_TO_KEEP);
            deleteOneRandomImage(allImagesLight);
            allImagesLight = getAllImagesLight();
        }
    }

    private void deleteOneRandomImage(Collection<Image> imageIds) {
        int rand = new Random().nextInt(imageIds.size());
        Image image = (Image) imageIds.toArray()[rand];
        deleteImageById(image.getId());
    }


    public Collection<Image> getAllImages() {
        Collection<Image> all_images = imageRepository.findAll();
        this.eventService.addFieldToActiveEvent("content.count", all_images.size());
        return all_images;
    }

    public Collection<Image> getAllImagesLight() {
        Collection<Image> all_image_ids = imageRepository.findAllIds();
        this.eventService.addFieldToActiveEvent("content.count", all_image_ids.size());
        return all_image_ids;
    }

    public Collection<Image> findWithNamesContaining(String fragment) {
        this.eventService.addFieldToActiveEvent("search.fragment", fragment);
        return imageRepository.findByNameContaining(fragment);
    }


    public Image getImageById(String id) {
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        return imageRepository.findById(id).orElse(null);
    }


    public boolean deleteImageById(String id) {
        this.eventService.addFieldToActiveEvent("content.imageId.toBeDeleted", id);
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

    public void deleteAllImages() {
        imageRepository.deleteAll();
    }

    public Image getRandomImage() {
        Collection<Image> allImages = getAllImagesLight();

        final Image image = allImages.stream()
                .skip((int) (allImages.size() * Math.random()))
                .findFirst().orElse(null);

        this.eventService.addFieldToActiveEvent("content.random.id", image.getId());
        return image != null ? getImageById(image.getId()) : null;
    }

    double numberOfImagesInDb() {
        return imageRepository.findAllIds().size();
    }
}

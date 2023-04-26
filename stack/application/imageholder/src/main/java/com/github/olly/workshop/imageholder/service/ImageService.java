package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import com.github.olly.workshop.springevents.service.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final int MAX_IMAGES_TO_KEEP = 1000;

    private final ImageRepository imageRepository;
    private final ImageHolderMetricsService imageHolderMetricsService;
    private final EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public Image save(Image image) {
        checkForImageLimit();
        image.setId(RandomStringUtils.randomAlphanumeric(20).toLowerCase());
        this.eventService.addFieldToActiveEvent("content.imageId", image.getId());
        Image save = imageRepository.save(image);
        imageHolderMetricsService.imageUploaded(image);
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
        this.eventService.addFieldToActiveEvent("total_images", all_images.size());
        return all_images;
    }

    public Collection<Image> getAllImagesLight() {
        Collection<Image> all_image_ids = imageRepository.findAllIds();
        this.eventService.addFieldToActiveEvent("total_images", all_image_ids.size());
        return all_image_ids;
    }

    public Collection<Image> findWithNamesContaining(String fragment) {
        this.eventService.addFieldToActiveEvent("search.fragment", fragment);
        Collection<Image> allImages = imageRepository.findAllIds();
        return allImages.stream().filter(i -> i.getName().contains(fragment)).collect(Collectors.toList());
    }


    public Image getImageById(String id) {
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        return imageRepository.findById(id).orElse(null);
    }


    public boolean deleteImageById(String id) {
        this.eventService.addFieldToActiveEvent("action", "delete");
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        Image image = getImageById(id);
        if (image != null) {
            imageRepository.deleteById(id);
            imageHolderMetricsService.imageDeleted(image);
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

        this.eventService.addFieldToActiveEvent("action", "delete_random");
        final String id = image != null ? image.getId() : "null";
        this.eventService.addFieldToActiveEvent("content.imageId", id);
        return getImageById(id);
    }
}

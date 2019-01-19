package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private static final Counter imagesUploaded = Counter.build()
            .name("application_images_uploaded_total")
            .labelNames("type")
            .help("Keep track of uploaded images").register();
    private static final Counter imagesViewed = Counter.build()
            .name("application_images_viewed_total")
            .labelNames("type", "name")
            .help("Keep track of viewed images").register();
    private static final Counter imagesDeleted = Counter.build()
            .name("application_images_deleted_total")
            .labelNames("type", "name")
            .help("Keep track of deleted images").register();
    private static final Gauge imagesInDatabase = Gauge.build()
            .name("application_images_in_db")
            .labelNames("type")
            .help("Keep track of uploaded images").register();

    public void imageInDatabase(Image image) {
        imagesInDatabase.labels(image.getContentType()).inc();
    }

    public void imageUploaded(Image image) {
        imagesUploaded.labels(image.getContentType()).inc();
        imagesInDatabase.labels(image.getContentType()).inc();
    }

    public void imageViewed(Image image) {
        imagesViewed.labels(image.getContentType(), image.getName()).inc();
    }

    public void imageDeleted(Image image) {
        imagesDeleted.labels(image.getContentType(), image.getName()).inc();
        imagesInDatabase.labels(image.getContentType()).dec();
    }
}

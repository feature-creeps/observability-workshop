package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public static Double numberOfImagesInDb;

    // TODO: impl gauge with micrometer
    //private static final Gauge imagesInDatabase = Gauge.build()
    //        .name("application_images_in_db")
    //        .labelNames("type")
    //        .help("Keep track of uploaded images").register();

    //public void imageInDatabase(Image image) {
    //    imagesInDatabase.labels(image.getContentType()).inc();
    //}

    public void imagesInDatabase(Image image) {
        HashSet tags = new HashSet();
        tags.add("type");
        tags.add(image.getContentType());
        Metrics.gauge("application_images_in_db", tags, numberOfImagesInDb);
        //imagesInDatabase.labels(image.getContentType()).inc();
    }

    public void imageUploaded(Image image) {
        Metrics.counter("application_images_uploaded_total",
                "type", image.getContentType())
                .increment();
        //imagesInDatabase.labels(image.getContentType()).inc();
    }

    public void imageViewed(Image image) {
        Metrics.counter("application_images_viewed_total",
                "type", image.getContentType(),
                "name", image.getName())
                .increment();
    }

    public void imageDeleted(Image image) {
        Metrics.counter("application_images_deleted_total",
                "type", image.getContentType(),
                "name", image.getName())
                .increment();
        //imagesInDatabase.labels(image.getContentType()).dec();
    }
}

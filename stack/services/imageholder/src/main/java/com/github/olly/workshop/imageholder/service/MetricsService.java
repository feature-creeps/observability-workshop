package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Metric;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public static Double numberOfImagesInDb;

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

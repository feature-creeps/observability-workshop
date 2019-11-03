package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MeterRegistry registry;

    private final ImageService imageService;

    private static Gauge imagesInDatabase;

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public MetricsService(MeterRegistry registry, ImageService imageService) {
        this.registry = registry;
        this.imageService = imageService;

        if (BUSINESS_METRICS_ENABLED) {
            imagesInDatabase = Gauge
                    .builder("application_images_in_database", imageService, ImageService::numberOfImagesInDb)
                    .register(registry);
        }
    }


    public void imageUploaded(Image image) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_uploaded_total",
                    "type", image.getContentType())
                    .increment();
        }
    }

    public void imageViewed(Image image) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_viewed_total",
                    "type", image.getContentType(),
                    "name", image.getName())
                    .increment();
        }
    }

    public void imageDeleted(Image image) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_deleted_total",
                    "type", image.getContentType(),
                    "name", image.getName())
                    .increment();
        }
    }
}

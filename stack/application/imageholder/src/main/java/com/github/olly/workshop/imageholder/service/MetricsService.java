package com.github.olly.workshop.imageholder.service;

import com.github.olly.workshop.imageholder.model.Image;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MeterRegistry registry;

    private final ImageRepository imageRepository;

    private static Gauge imagesInDatabase;

    private final Boolean BUSINESS_METRICS_ENABLED;

    @Autowired
    public MetricsService(MeterRegistry registry, ImageRepository imageRepository,
                          @Value("${business.metrics.enabled:true}") Boolean businessMetricsEnabled) {
        this.registry = registry;
        this.imageRepository = imageRepository;
        this.BUSINESS_METRICS_ENABLED = businessMetricsEnabled;

        if (BUSINESS_METRICS_ENABLED) {
            imagesInDatabase = Gauge
                    .builder("application_images_in_database", this, MetricsService::numberOfImagesInDb)
                    .register(registry);
        }
    }

    private double numberOfImagesInDb() {
        return imageRepository.findAllIds().size();
    }

    public void imageUploaded(Image image) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_uploaded_total",
                    "type", image.getContentType(),
                    "name", image.getName())
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

    public void httpRequestReceived(String method, String handler, String status, String path) {
        Metrics.counter("http_requests_total",
                "method", method,
                "handler", handler,
                "status", status,
                "path", path).increment();
    }
}

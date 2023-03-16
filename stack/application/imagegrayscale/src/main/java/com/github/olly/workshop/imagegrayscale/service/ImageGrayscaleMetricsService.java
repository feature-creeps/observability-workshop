package com.github.olly.workshop.imagegrayscale.service;

import com.github.olly.workshop.springevents.service.MetricsService;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageGrayscaleMetricsService extends MetricsService {

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageToGrayscale(String contentType, int sizeInBytes) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_grayscale_total",
                    "type", contentType,
                    "size", Integer.toString(sizeInBytes))
                    .increment();
        }
    }
}

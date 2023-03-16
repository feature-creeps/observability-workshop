package com.github.olly.workshop.imageflip.service;

import com.github.olly.workshop.springevents.service.MetricsService;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageFlipMetricsService extends MetricsService {

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageFlipped(String contentType, String vertical, String horizontal) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_flipped_total",
                    "type", contentType,
                    "vertical", vertical,
                    "horizontal", horizontal)
                    .increment();
        }
    }
}

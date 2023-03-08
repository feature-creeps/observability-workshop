package com.github.olly.workshop.imagerotator.service;

import com.github.olly.workshop.springevents.service.MetricsService;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageRotatorMetricsService extends MetricsService {

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageRotated(String contentType, String degrees) {
        // we know very well, that degrees is a bad example of a dimension as it has
        // unlimited possible values
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_rotated_total",
                    "type", contentType,
                    "degrees", degrees)
                    .increment();
        }
    }

}

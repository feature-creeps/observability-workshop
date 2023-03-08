package com.github.olly.workshop.imageresize.service;

import com.github.olly.workshop.springevents.service.MetricsService;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageResizeMetricsService extends MetricsService {

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageResized(String contentType, String factor) {
        // we know very well, that factor is a bad example of a dimension as it has
        // unlimited possible values
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_resized_total",
                    "type", contentType,
                    "factor", factor)
                    .increment();
        }
    }

}

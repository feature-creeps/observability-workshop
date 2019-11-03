package com.github.olly.workshop.imagethumbnail.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageThumbnailed(String contentType) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_thumbnailed_total",
                    "type", contentType)
                    .increment();
        }
    }

    private MetricsService() {
        if (BUSINESS_METRICS_ENABLED) {
            Gauge.builder("application_images_thumbnails_cached", ImageService.CACHE, Map::size)
                    .register(Metrics.globalRegistry);
        }
    }
}

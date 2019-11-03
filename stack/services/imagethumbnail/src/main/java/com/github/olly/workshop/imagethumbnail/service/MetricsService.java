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

    private final Boolean BUSINESS_METRICS_ENABLED;

    public void imageThumbnailed(String contentType) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_thumbnailed_total",
                    "type", contentType)
                    .increment();
        }
    }

    @Autowired
    private MetricsService(@Value("${business.metrics.enabled:true}") Boolean businessMetricsEnabled) {
        this.BUSINESS_METRICS_ENABLED = businessMetricsEnabled;

        if (BUSINESS_METRICS_ENABLED) {
            Gauge.builder("application_images_thumbnails_cached", ImageService.CACHE, Map::size)
                    .register(Metrics.globalRegistry);
        }
    }
}

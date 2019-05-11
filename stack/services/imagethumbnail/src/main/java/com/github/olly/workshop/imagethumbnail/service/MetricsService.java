package com.github.olly.workshop.imagethumbnail.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void imageThumbnailed(String contentType) {
        Metrics.counter("application_images_thumbnailed_total",
                "type", contentType)
                .increment();
    }

}

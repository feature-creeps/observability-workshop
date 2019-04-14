package com.github.olly.workshop.imageorchestrator.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void transformationPerformed(String type, String transformation) {
        Metrics.counter("application_transformations_total",
                "type", type,
                "transformation", transformation)
                .increment();
    }

    public void imageTransformed(String type) {
        Metrics.counter("application_image_transformed_total",
                "type", type)
                .increment();
    }
}

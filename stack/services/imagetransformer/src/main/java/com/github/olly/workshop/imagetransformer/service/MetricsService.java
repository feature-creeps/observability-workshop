package com.github.olly.workshop.imagetransformer.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void imageToGrayscale(String contentType, int sizeInBytes) {
        Metrics.counter("application_images_grayscale_total",
                "type", contentType,
                "size", Integer.toString(sizeInBytes))
                .increment();
    }

}

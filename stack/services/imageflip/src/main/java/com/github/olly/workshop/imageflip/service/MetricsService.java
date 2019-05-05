package com.github.olly.workshop.imageflip.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void imageFlipped(String contentType, String vertical, String horizontal) {
        Metrics.counter("application_images_flipped_total",
                "type", contentType,
                "vertical", vertical,
                "horizontal", horizontal)
                .increment();
    }
}

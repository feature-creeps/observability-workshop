package com.github.olly.workshop.imagerotator.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void imageRotated(String contentType, String degrees) {
        // we know very well, that degrees is a bad example of a dimension as it has unlimited possible values
        Metrics.counter("application_images_rotated_total",
                "type", contentType,
                "degrees", degrees)
                .increment();
    }

}

package com.github.olly.workshop.imageresize.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    public void imageResized(String contentType, String factor) {
        // we know very well, that factor is a bad example of a dimension as it has unlimited possible values
        Metrics.counter("application_images_resized_total",
                "type", contentType,
                "factor", factor)
                .increment();
    }

}

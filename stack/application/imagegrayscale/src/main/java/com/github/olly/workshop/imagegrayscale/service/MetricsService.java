package com.github.olly.workshop.imagegrayscale.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    @Autowired
    MeterRegistry registry;

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void imageToGrayscale(String contentType, int sizeInBytes) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_grayscale_total",
                    "type", contentType,
                    "size", Integer.toString(sizeInBytes))
                    .increment();
        }
    }

    public void httpRequestReceived(String method, String handler, String status, String path) {
        Metrics.counter("http_requests_total",
                "method", method,
                "handler", handler,
                "status", status,
                "path", path).increment();
    }
}

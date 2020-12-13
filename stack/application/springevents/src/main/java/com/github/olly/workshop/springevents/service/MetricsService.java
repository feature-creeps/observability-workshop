package com.github.olly.workshop.springevents.service;

import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    public void httpRequestReceived(String method, String handler, String status, String path) {
        Metrics.counter("http_requests_total",
                "method", method,
                "handler", handler,
                "status", status,
                "path", path).increment();
    }
}

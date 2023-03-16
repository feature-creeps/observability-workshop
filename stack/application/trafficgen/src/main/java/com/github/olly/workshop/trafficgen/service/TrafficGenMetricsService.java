package com.github.olly.workshop.trafficgen.service;

import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrafficGenMetricsService {

    @Value("${business.metrics.enabled:true}")
    private Boolean BUSINESS_METRICS_ENABLED;

    public void transformationPerformed(boolean successful) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("trafficgen_transformations_total",
                    "successful", String.valueOf(
                            successful))
                    .increment();
        }
    }

    public void uploadPerformed(boolean successful) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("trafficgen_uploads_total",
                    "successful", String.valueOf(
                            successful))
                    .increment();
        }
    }
}

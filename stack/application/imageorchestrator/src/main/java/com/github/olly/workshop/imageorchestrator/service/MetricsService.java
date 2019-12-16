package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.model.TransformationType;
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

    public void transformationPerformed(String type, String transformation) {
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_transformations_total",
                    "type", type,
                    "transformation", transformation)
                    .increment();
        }
    }

    public void imageTransformed(Image sourceImage, Image transformedImage, TransformationRequest transformationRequest) {
        // we know very well that sourceId is a very bad example for a label as it has unlimited possible values
        if (BUSINESS_METRICS_ENABLED) {
            Metrics.counter("application_images_transformed_total",
                    "sourceType", String.valueOf(sourceImage.getMimeType()),
                    "sourceId", String.valueOf(transformationRequest.getImageId()),
                    "targetType", String.valueOf(transformedImage.getMimeType()),
                    "persist", String.valueOf(transformationRequest.getPersist()),
                    "flip", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.flip)),
                    "grayscale", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.grayscale)),
                    "resize", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.resize)),
                    "rotate", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.rotate)))
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

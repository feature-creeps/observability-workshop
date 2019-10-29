package com.github.olly.workshop.imageorchestrator.service;

import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import com.github.olly.workshop.imageorchestrator.model.TransformationType;
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

    public void imageTransformed(Image sourceImage, Image transformedImage, TransformationRequest transformationRequest) {
        Metrics.counter("application_images_transformed_total",
                "sourceType", String.valueOf(sourceImage.getMimeType()),
                "sourceId", String.valueOf(sourceImage.getId()),
                "targetType", String.valueOf(transformedImage.getMimeType()),
                "persist", String.valueOf(transformationRequest.getPersist()),
                "flip", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.flip)),
                "grayscale", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.grayscale)),
                "resize", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.resize)),
                "rotate", String.valueOf(transformationRequest.getTransformationTypes().contains(TransformationType.rotate)))
                .increment();
    }
}

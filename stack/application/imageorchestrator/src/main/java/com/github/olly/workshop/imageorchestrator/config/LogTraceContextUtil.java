package com.github.olly.workshop.imageorchestrator.config;

import io.micrometer.tracing.SpanCustomizer;
import com.github.olly.workshop.imageorchestrator.model.Image;
import com.github.olly.workshop.imageorchestrator.model.Transformation;
import com.github.olly.workshop.imageorchestrator.model.TransformationRequest;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogTraceContextUtil {

    private final SpanCustomizer span;
    private final Boolean TRACING_TAGS_ENABLED;

    public LogTraceContextUtil(SpanCustomizer span,
                               @Value("${TRACING_TAGS_ENABLED:true}") Boolean tracingTagsEnabled) {
        this.span = span;
        this.TRACING_TAGS_ENABLED = tracingTagsEnabled;
    }

    public void put(Transformation transformation) {
        transformation(transformation);
    }

    public void put(Image image) {
        image(image);
    }

    public void put(TransformationRequest transformationRequest) {
        transformationRequest(transformationRequest);
    }

    public void put(Object... objects) {
        for (Object object : objects) {
            if (object instanceof Image) {
                image((Image) object);
            } else if (object instanceof Transformation) {
                transformation((Transformation) object);
            } else if (object instanceof TransformationRequest) {
                transformationRequest((TransformationRequest) object);
            }
        }
    }

    private void transformationRequest(TransformationRequest transformationRequest) {
        if (transformationRequest != null) {
            put("imageId", transformationRequest.getImageId());
            put("imageName", transformationRequest.getName());
            put("persist", transformationRequest.getPersist());
            put("transformationType", transformationRequest.getTransformationTypes());
            transformationRequest.getTransformations().forEach(LogTraceContextUtil::transformationProperties);
        }
    }

    private void transformation(Transformation transformation) {
        if (transformation != null) {
            put("transformationType", transformation.getType().name());
            transformationProperties(transformation);
        }
    }

    private static void transformationProperties(Transformation transformation) {
        if (transformation != null && transformation.getProperties() != null) {
            transformation.getProperties().forEach(MDC::put);
        }
    }

    private void image(Image image) {
        if (image != null) {
            put("mimeType", image.getMimeType());
            put("imageId", image.getId());
        }
    }

    private void put(String key, String value) {
        MDC.put(key, String.valueOf(value));
        if (TRACING_TAGS_ENABLED) {
            span.tag(key, String.valueOf(value));
        }
    }
}

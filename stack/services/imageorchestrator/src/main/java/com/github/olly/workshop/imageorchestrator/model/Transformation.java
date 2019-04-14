package com.github.olly.workshop.imageorchestrator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Map;

public class Transformation {

    private final TransformationType type;
    private final Map<String, String> properties;

    @JsonCreator
    public Transformation(@JsonProperty("type") TransformationType type,
                          @JsonProperty("properties") Map<String, String> properties) {
        this.type = type;
        this.properties = properties;
    }

    public TransformationType getType() {
        return type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("properties", properties)
                .toString();
    }
}

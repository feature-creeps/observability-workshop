package com.github.olly.workshop.trafficgen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}

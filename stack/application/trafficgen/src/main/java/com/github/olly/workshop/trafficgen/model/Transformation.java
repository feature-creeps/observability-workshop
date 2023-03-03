package com.github.olly.workshop.trafficgen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Random;

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

    private static Map<String, String> randomProperty(TransformationType transformationType) {
        Random random = new Random();
        switch (transformationType) {
            case rotate:
                return Map.<String, String>of("degrees", String.valueOf(random.nextInt(360)));
            case flip:
                return Map.<String, String>of(
                        "vertical", String.valueOf(random.nextBoolean()),
                        "horizontal", String.valueOf(random.nextBoolean()));
            case grayscale:
                return Map.of();
            case resize:
                return Map.<String, String>of("factor", String.valueOf(random.doubles()));
            default:
                return Map.of();
        }
    }

    public static Transformation random() {
        Random random = new Random();
        TransformationType randomType = TransformationType.values()[random.nextInt(
                TransformationType.values().length)];

        return new Transformation(randomType, randomProperty(randomType));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("properties", properties)
                .toString();
    }
}

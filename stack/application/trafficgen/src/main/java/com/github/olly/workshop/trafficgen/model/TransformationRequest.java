package com.github.olly.workshop.trafficgen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransformationRequest {

    private final String imageId;
    private final List<Transformation> transformations;
    private final Boolean persist;

    private final String name;

    @JsonCreator
    public TransformationRequest(@JsonProperty("imageId") String imageId,
            @JsonProperty("transformations") List<Transformation> transformations,
            @JsonProperty("persist") Boolean persist,
            @JsonProperty("name") String name) {
        this.imageId = imageId;
        this.transformations = transformations != null ? transformations : Collections.emptyList();
        this.persist = persist;
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public Boolean getPersist() {
        return persist;
    }

    public String getName() {
        return name;
    }

    public List<TransformationType> getTransformationTypes() {
        if (transformations != null) {
            return transformations.stream().map(Transformation::getType).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}

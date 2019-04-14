package com.github.olly.workshop.imageorchestrator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;


public class TransformationRequest {

    private final String imageId;
    private final List<Transformation> transformations;

    @JsonCreator
    public TransformationRequest(@JsonProperty("imageId") String imageId,
                                 @JsonProperty("transformations") List<Transformation> transformations) {
        this.imageId = imageId;
        this.transformations = transformations;
    }

    public String getImageId() {
        return imageId;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("imageId", imageId)
                .add("transformations", transformations)
                .toString();
    }
}

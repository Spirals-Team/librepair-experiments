package com.codesquad.library.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BookLocations {

    FRONT_DOOR_SIDE("문옆 책장"), FRONTEND_GROUP_SIDE("프론트엔드 책장"), BACKEND_SIDE("백엔드 책장");

    @JsonValue
    @JsonProperty("location")
    private String canonicalLocation;

    BookLocations(String canonicalLocation) {
        this.canonicalLocation = canonicalLocation;
    }
}

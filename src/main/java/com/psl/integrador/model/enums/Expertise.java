package com.psl.integrador.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Expertise {
    BEGINNER,
    INTERMEDIATE,
    EXPERT;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}

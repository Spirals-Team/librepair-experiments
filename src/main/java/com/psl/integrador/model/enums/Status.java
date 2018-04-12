package com.psl.integrador.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum Status implements Serializable {
    TO_OPEN,
    OPENED,
    CLOSED;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for X12CharacterSet.
 */
public enum X12CharacterSet {
    /** Enum value NotSpecified. */
    NOT_SPECIFIED("NotSpecified"),

    /** Enum value Basic. */
    BASIC("Basic"),

    /** Enum value Extended. */
    EXTENDED("Extended"),

    /** Enum value UTF8. */
    UTF8("UTF8");

    /** The actual serialized value for a X12CharacterSet instance. */
    private String value;

    X12CharacterSet(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a X12CharacterSet instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed X12CharacterSet object, or null if unable to parse.
     */
    @JsonCreator
    public static X12CharacterSet fromString(String value) {
        X12CharacterSet[] items = X12CharacterSet.values();
        for (X12CharacterSet item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}

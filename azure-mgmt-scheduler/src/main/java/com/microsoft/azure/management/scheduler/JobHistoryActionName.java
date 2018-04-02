/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.scheduler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for JobHistoryActionName.
 */
public enum JobHistoryActionName {
    /** Enum value MainAction. */
    MAIN_ACTION("MainAction"),

    /** Enum value ErrorAction. */
    ERROR_ACTION("ErrorAction");

    /** The actual serialized value for a JobHistoryActionName instance. */
    private String value;

    JobHistoryActionName(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a JobHistoryActionName instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed JobHistoryActionName object, or null if unable to parse.
     */
    @JsonCreator
    public static JobHistoryActionName fromString(String value) {
        JobHistoryActionName[] items = JobHistoryActionName.values();
        for (JobHistoryActionName item : items) {
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

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for MySqlMigrationType.
 */
public enum MySqlMigrationType {
    /** Enum value LocalToRemote. */
    LOCAL_TO_REMOTE("LocalToRemote"),

    /** Enum value RemoteToLocal. */
    REMOTE_TO_LOCAL("RemoteToLocal");

    /** The actual serialized value for a MySqlMigrationType instance. */
    private String value;

    MySqlMigrationType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a MySqlMigrationType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed MySqlMigrationType object, or null if unable to parse.
     */
    @JsonCreator
    public static MySqlMigrationType fromString(String value) {
        MySqlMigrationType[] items = MySqlMigrationType.values();
        for (MySqlMigrationType item : items) {
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

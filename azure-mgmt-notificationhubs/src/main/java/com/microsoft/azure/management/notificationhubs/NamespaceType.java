/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.notificationhubs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for NamespaceType.
 */
public enum NamespaceType {
    /** Enum value Messaging. */
    MESSAGING("Messaging"),

    /** Enum value NotificationHub. */
    NOTIFICATION_HUB("NotificationHub");

    /** The actual serialized value for a NamespaceType instance. */
    private String value;

    NamespaceType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a NamespaceType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed NamespaceType object, or null if unable to parse.
     */
    @JsonCreator
    public static NamespaceType fromString(String value) {
        NamespaceType[] items = NamespaceType.values();
        for (NamespaceType item : items) {
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

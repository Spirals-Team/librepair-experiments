/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for StorageType.
 */
public final class StorageType {
    /** Static value Standard for StorageType. */
    public static final StorageType STANDARD = new StorageType("Standard");

    /** Static value Premium for StorageType. */
    public static final StorageType PREMIUM = new StorageType("Premium");

    private String value;

    /**
     * Creates a custom value for StorageType.
     * @param value the custom value
     */
    public StorageType(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StorageType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        StorageType rhs = (StorageType) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.devices;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for Capabilities.
 */
public final class Capabilities {
    /** Static value None for Capabilities. */
    public static final Capabilities NONE = new Capabilities("None");

    /** Static value DeviceManagement for Capabilities. */
    public static final Capabilities DEVICE_MANAGEMENT = new Capabilities("DeviceManagement");

    private String value;

    /**
     * Creates a custom value for Capabilities.
     * @param value the custom value
     */
    public Capabilities(String value) {
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
        if (!(obj instanceof Capabilities)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Capabilities rhs = (Capabilities) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}

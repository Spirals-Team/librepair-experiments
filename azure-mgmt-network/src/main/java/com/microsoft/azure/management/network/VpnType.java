/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for VpnType.
 */
public final class VpnType {
    /** Static value PolicyBased for VpnType. */
    public static final VpnType POLICY_BASED = new VpnType("PolicyBased");

    /** Static value RouteBased for VpnType. */
    public static final VpnType ROUTE_BASED = new VpnType("RouteBased");

    private String value;

    /**
     * Creates a custom value for VpnType.
     * @param value the custom value
     */
    public VpnType(String value) {
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
        if (!(obj instanceof VpnType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        VpnType rhs = (VpnType) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}

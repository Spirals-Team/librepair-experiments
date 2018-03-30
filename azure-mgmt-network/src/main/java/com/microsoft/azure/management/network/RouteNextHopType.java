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
 * Defines values for RouteNextHopType.
 */
public final class RouteNextHopType {
    /** Static value VirtualNetworkGateway for RouteNextHopType. */
    public static final RouteNextHopType VIRTUAL_NETWORK_GATEWAY = new RouteNextHopType("VirtualNetworkGateway");

    /** Static value VnetLocal for RouteNextHopType. */
    public static final RouteNextHopType VNET_LOCAL = new RouteNextHopType("VnetLocal");

    /** Static value Internet for RouteNextHopType. */
    public static final RouteNextHopType INTERNET = new RouteNextHopType("Internet");

    /** Static value VirtualAppliance for RouteNextHopType. */
    public static final RouteNextHopType VIRTUAL_APPLIANCE = new RouteNextHopType("VirtualAppliance");

    /** Static value None for RouteNextHopType. */
    public static final RouteNextHopType NONE = new RouteNextHopType("None");

    private String value;

    /**
     * Creates a custom value for RouteNextHopType.
     * @param value the custom value
     */
    public RouteNextHopType(String value) {
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
        if (!(obj instanceof RouteNextHopType)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        RouteNextHopType rhs = (RouteNextHopType) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}

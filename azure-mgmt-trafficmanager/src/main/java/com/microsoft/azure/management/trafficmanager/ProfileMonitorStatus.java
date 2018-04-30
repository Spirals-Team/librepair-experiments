/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.trafficmanager;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ProfileMonitorStatus.
 */
public final class ProfileMonitorStatus {
    /** Static value CheckingEndpoints for ProfileMonitorStatus. */
    public static final ProfileMonitorStatus CHECKING_ENDPOINTS = new ProfileMonitorStatus("CheckingEndpoints");

    /** Static value Online for ProfileMonitorStatus. */
    public static final ProfileMonitorStatus ONLINE = new ProfileMonitorStatus("Online");

    /** Static value Degraded for ProfileMonitorStatus. */
    public static final ProfileMonitorStatus DEGRADED = new ProfileMonitorStatus("Degraded");

    /** Static value Disabled for ProfileMonitorStatus. */
    public static final ProfileMonitorStatus DISABLED = new ProfileMonitorStatus("Disabled");

    /** Static value Inactive for ProfileMonitorStatus. */
    public static final ProfileMonitorStatus INACTIVE = new ProfileMonitorStatus("Inactive");

    private String value;

    /**
     * Creates a custom value for ProfileMonitorStatus.
     * @param value the custom value
     */
    public ProfileMonitorStatus(String value) {
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
        if (!(obj instanceof ProfileMonitorStatus)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ProfileMonitorStatus rhs = (ProfileMonitorStatus) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}

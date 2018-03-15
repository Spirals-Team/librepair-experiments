/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The correlation property.
 */
public class Correlation {
    /**
     * The client tracking id.
     */
    @JsonProperty(value = "clientTrackingId")
    private String clientTrackingId;

    /**
     * Get the clientTrackingId value.
     *
     * @return the clientTrackingId value
     */
    public String clientTrackingId() {
        return this.clientTrackingId;
    }

    /**
     * Set the clientTrackingId value.
     *
     * @param clientTrackingId the clientTrackingId value to set
     * @return the Correlation object itself.
     */
    public Correlation withClientTrackingId(String clientTrackingId) {
        this.clientTrackingId = clientTrackingId;
        return this;
    }

}

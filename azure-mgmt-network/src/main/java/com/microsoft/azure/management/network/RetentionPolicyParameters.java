/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameters that define the retention policy for flow log.
 */
public class RetentionPolicyParameters {
    /**
     * Number of days to retain flow log records.
     */
    @JsonProperty(value = "days")
    private Integer days;

    /**
     * Flag to enable/disable retention.
     */
    @JsonProperty(value = "enabled")
    private Boolean enabled;

    /**
     * Get the days value.
     *
     * @return the days value
     */
    public Integer days() {
        return this.days;
    }

    /**
     * Set the days value.
     *
     * @param days the days value to set
     * @return the RetentionPolicyParameters object itself.
     */
    public RetentionPolicyParameters withDays(Integer days) {
        this.days = days;
        return this;
    }

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the RetentionPolicyParameters object itself.
     */
    public RetentionPolicyParameters withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

}

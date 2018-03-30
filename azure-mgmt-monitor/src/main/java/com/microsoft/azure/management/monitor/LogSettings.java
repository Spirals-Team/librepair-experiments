/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of MultiTenantDiagnosticSettings. Specifies the settings for a
 * particular log.
 */
public class LogSettings {
    /**
     * Name of a Diagnostic Log category for a resource type this setting is
     * applied to. To obtain the list of Diagnostic Log categories for a
     * resource, first perform a GET diagnostic settings operation.
     */
    @JsonProperty(value = "category")
    private String category;

    /**
     * a value indicating whether this log is enabled.
     */
    @JsonProperty(value = "enabled", required = true)
    private boolean enabled;

    /**
     * the retention policy for this log.
     */
    @JsonProperty(value = "retentionPolicy")
    private RetentionPolicy retentionPolicy;

    /**
     * Get the category value.
     *
     * @return the category value
     */
    public String category() {
        return this.category;
    }

    /**
     * Set the category value.
     *
     * @param category the category value to set
     * @return the LogSettings object itself.
     */
    public LogSettings withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the LogSettings object itself.
     */
    public LogSettings withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the retentionPolicy value.
     *
     * @return the retentionPolicy value
     */
    public RetentionPolicy retentionPolicy() {
        return this.retentionPolicy;
    }

    /**
     * Set the retentionPolicy value.
     *
     * @param retentionPolicy the retentionPolicy value to set
     * @return the LogSettings object itself.
     */
    public LogSettings withRetentionPolicy(RetentionPolicy retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
        return this;
    }

}

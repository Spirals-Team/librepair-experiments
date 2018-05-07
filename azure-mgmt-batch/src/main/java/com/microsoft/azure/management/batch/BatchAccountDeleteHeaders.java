/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines headers for Delete operation.
 */
public class BatchAccountDeleteHeaders {
    /**
     * The URL of the resource used to check the status of the asynchronous
     * operation.
     */
    @JsonProperty(value = "Location")
    private String location;

    /**
     * Suggested delay to check the status of the asynchronous operation. The
     * value is an integer that specifies the delay in seconds.
     */
    @JsonProperty(value = "Retry-After")
    private Integer retryAfter;

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set
     * @return the BatchAccountDeleteHeaders object itself.
     */
    public BatchAccountDeleteHeaders withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the retryAfter value.
     *
     * @return the retryAfter value
     */
    public Integer retryAfter() {
        return this.retryAfter;
    }

    /**
     * Set the retryAfter value.
     *
     * @param retryAfter the retryAfter value to set
     * @return the BatchAccountDeleteHeaders object itself.
     */
    public BatchAccountDeleteHeaders withRetryAfter(Integer retryAfter) {
        this.retryAfter = retryAfter;
        return this;
    }

}

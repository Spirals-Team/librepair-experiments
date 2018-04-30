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
 * Defines headers for Create operation.
 */
public class PoolCreateHeaders {
    /**
     * The ETag HTTP response header. This is an opaque string. You can use it
     * to detect whether the resource has changed between requests. In
     * particular, you can pass the ETag to one of the If-Match or
     * If-None-Match headers.
     */
    @JsonProperty(value = "ETag")
    private String eTag;

    /**
     * Get the eTag value.
     *
     * @return the eTag value
     */
    public String eTag() {
        return this.eTag;
    }

    /**
     * Set the eTag value.
     *
     * @param eTag the eTag value to set
     * @return the PoolCreateHeaders object itself.
     */
    public PoolCreateHeaders withETag(String eTag) {
        this.eTag = eTag;
        return this;
    }

}

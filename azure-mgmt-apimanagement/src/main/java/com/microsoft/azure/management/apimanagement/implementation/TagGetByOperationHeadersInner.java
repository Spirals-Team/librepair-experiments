/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines headers for GetByOperation operation.
 */
public class TagGetByOperationHeadersInner {
    /**
     * Current entity state version. Should be treated as opaque and used to
     * make conditional HTTP requests.
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
     * @return the TagGetByOperationHeadersInner object itself.
     */
    public TagGetByOperationHeadersInner withETag(String eTag) {
        this.eTag = eTag;
        return this;
    }

}

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
 * A name-value pair associated with a Batch service resource.
 * The Batch service does not assign any meaning to this metadata; it is solely
 * for the use of user code.
 */
public class MetadataItem {
    /**
     * The name of the metadata item.
     */
    @JsonProperty(value = "name", required = true)
    private String name;

    /**
     * The value of the metadata item.
     */
    @JsonProperty(value = "value", required = true)
    private String value;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the MetadataItem object itself.
     */
    public MetadataItem withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the MetadataItem object itself.
     */
    public MetadataItem withValue(String value) {
        this.value = value;
        return this;
    }

}

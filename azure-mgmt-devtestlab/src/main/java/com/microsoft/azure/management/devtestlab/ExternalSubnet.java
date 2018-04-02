/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Subnet information as returned by the Microsoft.Network API.
 */
public class ExternalSubnet {
    /**
     * Gets or sets the identifier.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * Gets or sets the name.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id value.
     *
     * @param id the id value to set
     * @return the ExternalSubnet object itself.
     */
    public ExternalSubnet withId(String id) {
        this.id = id;
        return this;
    }

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
     * @return the ExternalSubnet object itself.
     */
    public ExternalSubnet withName(String name) {
        this.name = name;
        return this;
    }

}

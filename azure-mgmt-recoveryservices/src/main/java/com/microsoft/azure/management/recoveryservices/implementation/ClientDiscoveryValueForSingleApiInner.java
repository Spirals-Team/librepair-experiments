/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservices.implementation;

import com.microsoft.azure.management.recoveryservices.ClientDiscoveryDisplay;
import com.microsoft.azure.management.recoveryservices.ClientDiscoveryForServiceSpecification;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Available operation details.
 */
@JsonFlatten
public class ClientDiscoveryValueForSingleApiInner {
    /**
     * Name of the operation.
     */
    @JsonProperty(value = "Name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * Contains the localized display information for this particular
     * operation.
     */
    @JsonProperty(value = "Display", access = JsonProperty.Access.WRITE_ONLY)
    private ClientDiscoveryDisplay display;

    /**
     * The intended executor of the operation.
     */
    @JsonProperty(value = "Origin", access = JsonProperty.Access.WRITE_ONLY)
    private String origin;

    /**
     * Operation properties.
     */
    @JsonProperty(value = "Properties.serviceSpecification", access = JsonProperty.Access.WRITE_ONLY)
    private ClientDiscoveryForServiceSpecification serviceSpecification;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the display value.
     *
     * @return the display value
     */
    public ClientDiscoveryDisplay display() {
        return this.display;
    }

    /**
     * Get the origin value.
     *
     * @return the origin value
     */
    public String origin() {
        return this.origin;
    }

    /**
     * Get the serviceSpecification value.
     *
     * @return the serviceSpecification value
     */
    public ClientDiscoveryForServiceSpecification serviceSpecification() {
        return this.serviceSpecification;
    }

}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Instance of an app.
 */
@JsonFlatten
public class SiteInstanceInner extends ProxyOnlyResource {
    /**
     * Name of instance.
     */
    @JsonProperty(value = "properties.name", access = JsonProperty.Access.WRITE_ONLY)
    private String siteInstanceName;

    /**
     * Get the siteInstanceName value.
     *
     * @return the siteInstanceName value
     */
    public String siteInstanceName() {
        return this.siteInstanceName;
    }

}

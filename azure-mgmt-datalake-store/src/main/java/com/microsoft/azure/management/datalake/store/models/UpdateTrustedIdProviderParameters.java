/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.store.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters used to update a trusted identity provider.
 */
@JsonFlatten
public class UpdateTrustedIdProviderParameters {
    /**
     * The URL of this trusted identity provider.
     */
    @JsonProperty(value = "properties.idProvider")
    private String idProvider;

    /**
     * Get the idProvider value.
     *
     * @return the idProvider value
     */
    public String idProvider() {
        return this.idProvider;
    }

    /**
     * Set the idProvider value.
     *
     * @param idProvider the idProvider value to set
     * @return the UpdateTrustedIdProviderParameters object itself.
     */
    public UpdateTrustedIdProviderParameters withIdProvider(String idProvider) {
        this.idProvider = idProvider;
        return this;
    }

}

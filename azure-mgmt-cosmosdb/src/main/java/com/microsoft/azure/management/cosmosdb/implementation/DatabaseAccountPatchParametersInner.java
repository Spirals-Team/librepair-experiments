/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.implementation;

import java.util.Map;
import java.util.List;
import com.microsoft.azure.management.cosmosdb.Capability;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Parameters for patching Azure Cosmos DB database account properties.
 */
@JsonFlatten
public class DatabaseAccountPatchParametersInner {
    /**
     * The tags property.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * List of Cosmos DB capabilities for the account.
     */
    @JsonProperty(value = "properties.capabilities")
    private List<Capability> capabilities;

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set
     * @return the DatabaseAccountPatchParametersInner object itself.
     */
    public DatabaseAccountPatchParametersInner withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the capabilities value.
     *
     * @return the capabilities value
     */
    public List<Capability> capabilities() {
        return this.capabilities;
    }

    /**
     * Set the capabilities value.
     *
     * @param capabilities the capabilities value to set
     * @return the DatabaseAccountPatchParametersInner object itself.
     */
    public DatabaseAccountPatchParametersInner withCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
        return this;
    }

}

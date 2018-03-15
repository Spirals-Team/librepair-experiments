/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.powerbi.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The MigrateWorkspaceCollectionRequestInner model.
 */
public class MigrateWorkspaceCollectionRequestInner {
    /**
     * Name of the resource group the Power BI workspace collections will be
     * migrated to.
     */
    @JsonProperty(value = "targetResourceGroup")
    private String targetResourceGroup;

    /**
     * The resources property.
     */
    @JsonProperty(value = "resources")
    private List<String> resources;

    /**
     * Get the targetResourceGroup value.
     *
     * @return the targetResourceGroup value
     */
    public String targetResourceGroup() {
        return this.targetResourceGroup;
    }

    /**
     * Set the targetResourceGroup value.
     *
     * @param targetResourceGroup the targetResourceGroup value to set
     * @return the MigrateWorkspaceCollectionRequestInner object itself.
     */
    public MigrateWorkspaceCollectionRequestInner withTargetResourceGroup(String targetResourceGroup) {
        this.targetResourceGroup = targetResourceGroup;
        return this;
    }

    /**
     * Get the resources value.
     *
     * @return the resources value
     */
    public List<String> resources() {
        return this.resources;
    }

    /**
     * Set the resources value.
     *
     * @param resources the resources value to set
     * @return the MigrateWorkspaceCollectionRequestInner object itself.
     */
    public MigrateWorkspaceCollectionRequestInner withResources(List<String> resources) {
        this.resources = resources;
        return this;
    }

}

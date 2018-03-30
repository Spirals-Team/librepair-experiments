/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservices.implementation;

import com.microsoft.azure.management.recoveryservices.StorageModelType;
import com.microsoft.azure.management.recoveryservices.StorageType;
import com.microsoft.azure.management.recoveryservices.StorageTypeState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The backup storage config.
 */
@JsonFlatten
public class BackupStorageConfigInner extends Resource {
    /**
     * Storage model type. Possible values include: 'Invalid', 'GeoRedundant',
     * 'LocallyRedundant'.
     */
    @JsonProperty(value = "properties.storageModelType")
    private StorageModelType storageModelType;

    /**
     * Storage type. Possible values include: 'Invalid', 'GeoRedundant',
     * 'LocallyRedundant'.
     */
    @JsonProperty(value = "properties.storageType")
    private StorageType storageType;

    /**
     * Locked or Unlocked. Once a machine is registered against a resource, the
     * storageTypeState is always Locked. Possible values include: 'Invalid',
     * 'Locked', 'Unlocked'.
     */
    @JsonProperty(value = "properties.storageTypeState")
    private StorageTypeState storageTypeState;

    /**
     * Get the storageModelType value.
     *
     * @return the storageModelType value
     */
    public StorageModelType storageModelType() {
        return this.storageModelType;
    }

    /**
     * Set the storageModelType value.
     *
     * @param storageModelType the storageModelType value to set
     * @return the BackupStorageConfigInner object itself.
     */
    public BackupStorageConfigInner withStorageModelType(StorageModelType storageModelType) {
        this.storageModelType = storageModelType;
        return this;
    }

    /**
     * Get the storageType value.
     *
     * @return the storageType value
     */
    public StorageType storageType() {
        return this.storageType;
    }

    /**
     * Set the storageType value.
     *
     * @param storageType the storageType value to set
     * @return the BackupStorageConfigInner object itself.
     */
    public BackupStorageConfigInner withStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    /**
     * Get the storageTypeState value.
     *
     * @return the storageTypeState value
     */
    public StorageTypeState storageTypeState() {
        return this.storageTypeState;
    }

    /**
     * Set the storageTypeState value.
     *
     * @param storageTypeState the storageTypeState value to set
     * @return the BackupStorageConfigInner object itself.
     */
    public BackupStorageConfigInner withStorageTypeState(StorageTypeState storageTypeState) {
        this.storageTypeState = storageTypeState;
        return this;
    }

}

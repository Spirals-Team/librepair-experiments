/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batchai;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Settings for the data disk which would be created for the File Server.
 */
public class DataDisks {
    /**
     * Initial disk size in GB for blank data disks, and the new desired size
     * for resizing existing data disks.
     */
    @JsonProperty(value = "diskSizeInGB", required = true)
    private int diskSizeInGB;

    /**
     * None, ReadOnly, ReadWrite. Default value is None. This property is not
     * patchable.
     * Possible values include: 'none', 'readonly', 'readwrite'.
     */
    @JsonProperty(value = "cachingType")
    private CachingType cachingType;

    /**
     * Number of data disks to be attached to the VM. RAID level 0 will be
     * applied in the case of multiple disks.
     */
    @JsonProperty(value = "diskCount", required = true)
    private int diskCount;

    /**
     * Specifies the type of storage account to be used on the disk. Possible
     * values are: Standard_LRS or Premium_LRS.
     * Possible values include: 'Standard_LRS', 'Premium_LRS'.
     */
    @JsonProperty(value = "storageAccountType", required = true)
    private StorageAccountType storageAccountType;

    /**
     * Get the diskSizeInGB value.
     *
     * @return the diskSizeInGB value
     */
    public int diskSizeInGB() {
        return this.diskSizeInGB;
    }

    /**
     * Set the diskSizeInGB value.
     *
     * @param diskSizeInGB the diskSizeInGB value to set
     * @return the DataDisks object itself.
     */
    public DataDisks withDiskSizeInGB(int diskSizeInGB) {
        this.diskSizeInGB = diskSizeInGB;
        return this;
    }

    /**
     * Get the cachingType value.
     *
     * @return the cachingType value
     */
    public CachingType cachingType() {
        return this.cachingType;
    }

    /**
     * Set the cachingType value.
     *
     * @param cachingType the cachingType value to set
     * @return the DataDisks object itself.
     */
    public DataDisks withCachingType(CachingType cachingType) {
        this.cachingType = cachingType;
        return this;
    }

    /**
     * Get the diskCount value.
     *
     * @return the diskCount value
     */
    public int diskCount() {
        return this.diskCount;
    }

    /**
     * Set the diskCount value.
     *
     * @param diskCount the diskCount value to set
     * @return the DataDisks object itself.
     */
    public DataDisks withDiskCount(int diskCount) {
        this.diskCount = diskCount;
        return this;
    }

    /**
     * Get the storageAccountType value.
     *
     * @return the storageAccountType value
     */
    public StorageAccountType storageAccountType() {
        return this.storageAccountType;
    }

    /**
     * Set the storageAccountType value.
     *
     * @param storageAccountType the storageAccountType value to set
     * @return the DataDisks object itself.
     */
    public DataDisks withStorageAccountType(StorageAccountType storageAccountType) {
        this.storageAccountType = storageAccountType;
        return this;
    }

}

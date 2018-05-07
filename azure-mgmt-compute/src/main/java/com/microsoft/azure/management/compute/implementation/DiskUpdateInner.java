/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.management.compute.OperatingSystemTypes;
import com.microsoft.azure.management.compute.EncryptionSettings;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.compute.ResourceUpdate;

/**
 * Disk update resource.
 */
@JsonFlatten
public class DiskUpdateInner extends ResourceUpdate {
    /**
     * the Operating System type. Possible values include: 'Windows', 'Linux'.
     */
    @JsonProperty(value = "properties.osType")
    private OperatingSystemTypes osType;

    /**
     * If creationData.createOption is Empty, this field is mandatory and it
     * indicates the size of the VHD to create. If this field is present for
     * updates or creation with other options, it indicates a resize. Resizes
     * are only allowed if the disk is not attached to a running VM, and can
     * only increase the disk's size.
     */
    @JsonProperty(value = "properties.diskSizeGB")
    private Integer diskSizeGB;

    /**
     * Encryption settings for disk or snapshot.
     */
    @JsonProperty(value = "properties.encryptionSettings")
    private EncryptionSettings encryptionSettings;

    /**
     * Get the osType value.
     *
     * @return the osType value
     */
    public OperatingSystemTypes osType() {
        return this.osType;
    }

    /**
     * Set the osType value.
     *
     * @param osType the osType value to set
     * @return the DiskUpdateInner object itself.
     */
    public DiskUpdateInner withOsType(OperatingSystemTypes osType) {
        this.osType = osType;
        return this;
    }

    /**
     * Get the diskSizeGB value.
     *
     * @return the diskSizeGB value
     */
    public Integer diskSizeGB() {
        return this.diskSizeGB;
    }

    /**
     * Set the diskSizeGB value.
     *
     * @param diskSizeGB the diskSizeGB value to set
     * @return the DiskUpdateInner object itself.
     */
    public DiskUpdateInner withDiskSizeGB(Integer diskSizeGB) {
        this.diskSizeGB = diskSizeGB;
        return this;
    }

    /**
     * Get the encryptionSettings value.
     *
     * @return the encryptionSettings value
     */
    public EncryptionSettings encryptionSettings() {
        return this.encryptionSettings;
    }

    /**
     * Set the encryptionSettings value.
     *
     * @param encryptionSettings the encryptionSettings value to set
     * @return the DiskUpdateInner object itself.
     */
    public DiskUpdateInner withEncryptionSettings(EncryptionSettings encryptionSettings) {
        this.encryptionSettings = encryptionSettings;
        return this;
    }

}

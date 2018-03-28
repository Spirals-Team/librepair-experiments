/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.compute.implementation.ImageReferenceInner;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies the storage settings for the virtual machine disks.
 */
public class StorageProfile {
    /**
     * Specifies information about the image to use. You can specify
     * information about platform images, marketplace images, or virtual
     * machine images. This element is required when you want to use a platform
     * image, marketplace image, or virtual machine image, but is not used in
     * other creation operations.
     */
    @JsonProperty(value = "imageReference")
    private ImageReferenceInner imageReference;

    /**
     * Specifies information about the operating system disk used by the
     * virtual machine. &lt;br&gt;&lt;br&gt; For more information about disks,
     * see [About disks and VHDs for Azure virtual
     * machines](https://docs.microsoft.com/azure/virtual-machines/virtual-machines-windows-about-disks-vhds?toc=%2fazure%2fvirtual-machines%2fwindows%2ftoc.json).
     */
    @JsonProperty(value = "osDisk")
    private OSDisk osDisk;

    /**
     * Specifies the parameters that are used to add a data disk to a virtual
     * machine. &lt;br&gt;&lt;br&gt; For more information about disks, see
     * [About disks and VHDs for Azure virtual
     * machines](https://docs.microsoft.com/azure/virtual-machines/virtual-machines-windows-about-disks-vhds?toc=%2fazure%2fvirtual-machines%2fwindows%2ftoc.json).
     */
    @JsonProperty(value = "dataDisks")
    private List<DataDisk> dataDisks;

    /**
     * Get the imageReference value.
     *
     * @return the imageReference value
     */
    public ImageReferenceInner imageReference() {
        return this.imageReference;
    }

    /**
     * Set the imageReference value.
     *
     * @param imageReference the imageReference value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withImageReference(ImageReferenceInner imageReference) {
        this.imageReference = imageReference;
        return this;
    }

    /**
     * Get the osDisk value.
     *
     * @return the osDisk value
     */
    public OSDisk osDisk() {
        return this.osDisk;
    }

    /**
     * Set the osDisk value.
     *
     * @param osDisk the osDisk value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withOsDisk(OSDisk osDisk) {
        this.osDisk = osDisk;
        return this;
    }

    /**
     * Get the dataDisks value.
     *
     * @return the dataDisks value
     */
    public List<DataDisk> dataDisks() {
        return this.dataDisks;
    }

    /**
     * Set the dataDisks value.
     *
     * @param dataDisks the dataDisks value to set
     * @return the StorageProfile object itself.
     */
    public StorageProfile withDataDisks(List<DataDisk> dataDisks) {
        this.dataDisks = dataDisks;
        return this;
    }

}

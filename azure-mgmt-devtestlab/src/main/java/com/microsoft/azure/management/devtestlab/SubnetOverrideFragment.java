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
 * Property overrides on a subnet of a virtual network.
 */
public class SubnetOverrideFragment {
    /**
     * The resource ID of the subnet.
     */
    @JsonProperty(value = "resourceId")
    private String resourceId;

    /**
     * The name given to the subnet within the lab.
     */
    @JsonProperty(value = "labSubnetName")
    private String labSubnetName;

    /**
     * Indicates whether this subnet can be used during virtual machine
     * creation (i.e. Allow, Deny). Possible values include: 'Default', 'Deny',
     * 'Allow'.
     */
    @JsonProperty(value = "useInVmCreationPermission")
    private UsagePermissionType useInVmCreationPermission;

    /**
     * Indicates whether public IP addresses can be assigned to virtual
     * machines on this subnet (i.e. Allow, Deny). Possible values include:
     * 'Default', 'Deny', 'Allow'.
     */
    @JsonProperty(value = "usePublicIpAddressPermission")
    private UsagePermissionType usePublicIpAddressPermission;

    /**
     * Properties that virtual machines on this subnet will share.
     */
    @JsonProperty(value = "sharedPublicIpAddressConfiguration")
    private SubnetSharedPublicIpAddressConfigurationFragment sharedPublicIpAddressConfiguration;

    /**
     * The virtual network pool associated with this subnet.
     */
    @JsonProperty(value = "virtualNetworkPoolName")
    private String virtualNetworkPoolName;

    /**
     * Get the resourceId value.
     *
     * @return the resourceId value
     */
    public String resourceId() {
        return this.resourceId;
    }

    /**
     * Set the resourceId value.
     *
     * @param resourceId the resourceId value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    /**
     * Get the labSubnetName value.
     *
     * @return the labSubnetName value
     */
    public String labSubnetName() {
        return this.labSubnetName;
    }

    /**
     * Set the labSubnetName value.
     *
     * @param labSubnetName the labSubnetName value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withLabSubnetName(String labSubnetName) {
        this.labSubnetName = labSubnetName;
        return this;
    }

    /**
     * Get the useInVmCreationPermission value.
     *
     * @return the useInVmCreationPermission value
     */
    public UsagePermissionType useInVmCreationPermission() {
        return this.useInVmCreationPermission;
    }

    /**
     * Set the useInVmCreationPermission value.
     *
     * @param useInVmCreationPermission the useInVmCreationPermission value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withUseInVmCreationPermission(UsagePermissionType useInVmCreationPermission) {
        this.useInVmCreationPermission = useInVmCreationPermission;
        return this;
    }

    /**
     * Get the usePublicIpAddressPermission value.
     *
     * @return the usePublicIpAddressPermission value
     */
    public UsagePermissionType usePublicIpAddressPermission() {
        return this.usePublicIpAddressPermission;
    }

    /**
     * Set the usePublicIpAddressPermission value.
     *
     * @param usePublicIpAddressPermission the usePublicIpAddressPermission value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withUsePublicIpAddressPermission(UsagePermissionType usePublicIpAddressPermission) {
        this.usePublicIpAddressPermission = usePublicIpAddressPermission;
        return this;
    }

    /**
     * Get the sharedPublicIpAddressConfiguration value.
     *
     * @return the sharedPublicIpAddressConfiguration value
     */
    public SubnetSharedPublicIpAddressConfigurationFragment sharedPublicIpAddressConfiguration() {
        return this.sharedPublicIpAddressConfiguration;
    }

    /**
     * Set the sharedPublicIpAddressConfiguration value.
     *
     * @param sharedPublicIpAddressConfiguration the sharedPublicIpAddressConfiguration value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withSharedPublicIpAddressConfiguration(SubnetSharedPublicIpAddressConfigurationFragment sharedPublicIpAddressConfiguration) {
        this.sharedPublicIpAddressConfiguration = sharedPublicIpAddressConfiguration;
        return this;
    }

    /**
     * Get the virtualNetworkPoolName value.
     *
     * @return the virtualNetworkPoolName value
     */
    public String virtualNetworkPoolName() {
        return this.virtualNetworkPoolName;
    }

    /**
     * Set the virtualNetworkPoolName value.
     *
     * @param virtualNetworkPoolName the virtualNetworkPoolName value to set
     * @return the SubnetOverrideFragment object itself.
     */
    public SubnetOverrideFragment withVirtualNetworkPoolName(String virtualNetworkPoolName) {
        this.virtualNetworkPoolName = virtualNetworkPoolName;
        return this;
    }

}

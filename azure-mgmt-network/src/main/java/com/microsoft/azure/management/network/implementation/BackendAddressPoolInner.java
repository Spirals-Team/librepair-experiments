/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.implementation;

import java.util.List;
import com.microsoft.azure.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Pool of backend IP addresses.
 */
@JsonFlatten
public class BackendAddressPoolInner extends SubResource {
    /**
     * Gets collection of references to IP addresses defined in network
     * interfaces.
     */
    @JsonProperty(value = "properties.backendIPConfigurations", access = JsonProperty.Access.WRITE_ONLY)
    private List<NetworkInterfaceIPConfigurationInner> backendIPConfigurations;

    /**
     * Gets load balancing rules that use this backend address pool.
     */
    @JsonProperty(value = "properties.loadBalancingRules", access = JsonProperty.Access.WRITE_ONLY)
    private List<SubResource> loadBalancingRules;

    /**
     * Gets outbound rules that use this backend address pool.
     */
    @JsonProperty(value = "properties.outboundNatRule", access = JsonProperty.Access.WRITE_ONLY)
    private SubResource outboundNatRule;

    /**
     * Get provisioning state of the public IP resource. Possible values are:
     * 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Gets name of the resource that is unique within a resource group. This
     * name can be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /**
     * Get the backendIPConfigurations value.
     *
     * @return the backendIPConfigurations value
     */
    public List<NetworkInterfaceIPConfigurationInner> backendIPConfigurations() {
        return this.backendIPConfigurations;
    }

    /**
     * Get the loadBalancingRules value.
     *
     * @return the loadBalancingRules value
     */
    public List<SubResource> loadBalancingRules() {
        return this.loadBalancingRules;
    }

    /**
     * Get the outboundNatRule value.
     *
     * @return the outboundNatRule value
     */
    public SubResource outboundNatRule() {
        return this.outboundNatRule;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState value.
     *
     * @param provisioningState the provisioningState value to set
     * @return the BackendAddressPoolInner object itself.
     */
    public BackendAddressPoolInner withProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the BackendAddressPoolInner object itself.
     */
    public BackendAddressPoolInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag value.
     *
     * @return the etag value
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set the etag value.
     *
     * @param etag the etag value to set
     * @return the BackendAddressPoolInner object itself.
     */
    public BackendAddressPoolInner withEtag(String etag) {
        this.etag = etag;
        return this;
    }

}

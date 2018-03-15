/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Virtual Network information contract.
 */
@JsonFlatten
public class VnetInfoInner extends ProxyOnlyResource {
    /**
     * The Virtual Network's resource ID.
     */
    @JsonProperty(value = "properties.vnetResourceId")
    private String vnetResourceId;

    /**
     * The client certificate thumbprint.
     */
    @JsonProperty(value = "properties.certThumbprint", access = JsonProperty.Access.WRITE_ONLY)
    private String certThumbprint;

    /**
     * A certificate file (.cer) blob containing the public key of the private
     * key used to authenticate a
     * Point-To-Site VPN connection.
     */
    @JsonProperty(value = "properties.certBlob")
    private byte[] certBlob;

    /**
     * The routes that this Virtual Network connection uses.
     */
    @JsonProperty(value = "properties.routes", access = JsonProperty.Access.WRITE_ONLY)
    private List<VnetRouteInner> routes;

    /**
     * &lt;code&gt;true&lt;/code&gt; if a resync is required; otherwise,
     * &lt;code&gt;false&lt;/code&gt;.
     */
    @JsonProperty(value = "properties.resyncRequired", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean resyncRequired;

    /**
     * DNS servers to be used by this Virtual Network. This should be a
     * comma-separated list of IP addresses.
     */
    @JsonProperty(value = "properties.dnsServers")
    private String dnsServers;

    /**
     * Get the vnetResourceId value.
     *
     * @return the vnetResourceId value
     */
    public String vnetResourceId() {
        return this.vnetResourceId;
    }

    /**
     * Set the vnetResourceId value.
     *
     * @param vnetResourceId the vnetResourceId value to set
     * @return the VnetInfoInner object itself.
     */
    public VnetInfoInner withVnetResourceId(String vnetResourceId) {
        this.vnetResourceId = vnetResourceId;
        return this;
    }

    /**
     * Get the certThumbprint value.
     *
     * @return the certThumbprint value
     */
    public String certThumbprint() {
        return this.certThumbprint;
    }

    /**
     * Get the certBlob value.
     *
     * @return the certBlob value
     */
    public byte[] certBlob() {
        return this.certBlob;
    }

    /**
     * Set the certBlob value.
     *
     * @param certBlob the certBlob value to set
     * @return the VnetInfoInner object itself.
     */
    public VnetInfoInner withCertBlob(byte[] certBlob) {
        this.certBlob = certBlob;
        return this;
    }

    /**
     * Get the routes value.
     *
     * @return the routes value
     */
    public List<VnetRouteInner> routes() {
        return this.routes;
    }

    /**
     * Get the resyncRequired value.
     *
     * @return the resyncRequired value
     */
    public Boolean resyncRequired() {
        return this.resyncRequired;
    }

    /**
     * Get the dnsServers value.
     *
     * @return the dnsServers value
     */
    public String dnsServers() {
        return this.dnsServers;
    }

    /**
     * Set the dnsServers value.
     *
     * @param dnsServers the dnsServers value to set
     * @return the VnetInfoInner object itself.
     */
    public VnetInfoInner withDnsServers(String dnsServers) {
        this.dnsServers = dnsServers;
        return this;
    }

}

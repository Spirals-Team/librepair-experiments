/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Virtual IP mapping.
 */
public class VirtualIPMapping {
    /**
     * Virtual IP address.
     */
    @JsonProperty(value = "virtualIP")
    private String virtualIP;

    /**
     * Internal HTTP port.
     */
    @JsonProperty(value = "internalHttpPort")
    private Integer internalHttpPort;

    /**
     * Internal HTTPS port.
     */
    @JsonProperty(value = "internalHttpsPort")
    private Integer internalHttpsPort;

    /**
     * Is virtual IP mapping in use.
     */
    @JsonProperty(value = "inUse")
    private Boolean inUse;

    /**
     * Get the virtualIP value.
     *
     * @return the virtualIP value
     */
    public String virtualIP() {
        return this.virtualIP;
    }

    /**
     * Set the virtualIP value.
     *
     * @param virtualIP the virtualIP value to set
     * @return the VirtualIPMapping object itself.
     */
    public VirtualIPMapping withVirtualIP(String virtualIP) {
        this.virtualIP = virtualIP;
        return this;
    }

    /**
     * Get the internalHttpPort value.
     *
     * @return the internalHttpPort value
     */
    public Integer internalHttpPort() {
        return this.internalHttpPort;
    }

    /**
     * Set the internalHttpPort value.
     *
     * @param internalHttpPort the internalHttpPort value to set
     * @return the VirtualIPMapping object itself.
     */
    public VirtualIPMapping withInternalHttpPort(Integer internalHttpPort) {
        this.internalHttpPort = internalHttpPort;
        return this;
    }

    /**
     * Get the internalHttpsPort value.
     *
     * @return the internalHttpsPort value
     */
    public Integer internalHttpsPort() {
        return this.internalHttpsPort;
    }

    /**
     * Set the internalHttpsPort value.
     *
     * @param internalHttpsPort the internalHttpsPort value to set
     * @return the VirtualIPMapping object itself.
     */
    public VirtualIPMapping withInternalHttpsPort(Integer internalHttpsPort) {
        this.internalHttpsPort = internalHttpsPort;
        return this;
    }

    /**
     * Get the inUse value.
     *
     * @return the inUse value
     */
    public Boolean inUse() {
        return this.inUse;
    }

    /**
     * Set the inUse value.
     *
     * @param inUse the inUse value to set
     * @return the VirtualIPMapping object itself.
     */
    public VirtualIPMapping withInUse(Boolean inUse) {
        this.inUse = inUse;
        return this;
    }

}

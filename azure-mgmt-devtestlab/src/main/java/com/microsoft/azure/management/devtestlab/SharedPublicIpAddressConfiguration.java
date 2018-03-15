/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties of a virtual machine that determine how it is connected to a load
 * balancer.
 */
public class SharedPublicIpAddressConfiguration {
    /**
     * The incoming NAT rules.
     */
    @JsonProperty(value = "inboundNatRules")
    private List<InboundNatRule> inboundNatRules;

    /**
     * Get the inboundNatRules value.
     *
     * @return the inboundNatRules value
     */
    public List<InboundNatRule> inboundNatRules() {
        return this.inboundNatRules;
    }

    /**
     * Set the inboundNatRules value.
     *
     * @param inboundNatRules the inboundNatRules value to set
     * @return the SharedPublicIpAddressConfiguration object itself.
     */
    public SharedPublicIpAddressConfiguration withInboundNatRules(List<InboundNatRule> inboundNatRules) {
        this.inboundNatRules = inboundNatRules;
        return this;
    }

}

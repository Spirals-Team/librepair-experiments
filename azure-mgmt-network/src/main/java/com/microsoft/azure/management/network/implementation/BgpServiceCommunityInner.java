/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.implementation;

import java.util.List;
import com.microsoft.azure.management.network.BGPCommunity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Service Community Properties.
 */
@JsonFlatten
public class BgpServiceCommunityInner extends Resource {
    /**
     * The name of the bgp community. e.g. Skype.
     */
    @JsonProperty(value = "properties.serviceName")
    private String serviceName;

    /**
     * Get a list of bgp communities.
     */
    @JsonProperty(value = "properties.bgpCommunities")
    private List<BGPCommunity> bgpCommunities;

    /**
     * Get the serviceName value.
     *
     * @return the serviceName value
     */
    public String serviceName() {
        return this.serviceName;
    }

    /**
     * Set the serviceName value.
     *
     * @param serviceName the serviceName value to set
     * @return the BgpServiceCommunityInner object itself.
     */
    public BgpServiceCommunityInner withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    /**
     * Get the bgpCommunities value.
     *
     * @return the bgpCommunities value
     */
    public List<BGPCommunity> bgpCommunities() {
        return this.bgpCommunities;
    }

    /**
     * Set the bgpCommunities value.
     *
     * @param bgpCommunities the bgpCommunities value to set
     * @return the BgpServiceCommunityInner object itself.
     */
    public BgpServiceCommunityInner withBgpCommunities(List<BGPCommunity> bgpCommunities) {
        this.bgpCommunities = bgpCommunities;
        return this;
    }

}

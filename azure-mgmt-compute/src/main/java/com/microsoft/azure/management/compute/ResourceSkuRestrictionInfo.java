/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ResourceSkuRestrictionInfo model.
 */
public class ResourceSkuRestrictionInfo {
    /**
     * Locations where the SKU is restricted.
     */
    @JsonProperty(value = "locations", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> locations;

    /**
     * List of availability zones where the SKU is restricted.
     */
    @JsonProperty(value = "zones", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> zones;

    /**
     * Get the locations value.
     *
     * @return the locations value
     */
    public List<String> locations() {
        return this.locations;
    }

    /**
     * Get the zones value.
     *
     * @return the zones value
     */
    public List<String> zones() {
        return this.zones;
    }

}

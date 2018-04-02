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
 * Describes scaling information of a SKU.
 */
public class ResourceSkuRestrictions {
    /**
     * The type of restrictions. Possible values include: 'Location'.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private ResourceSkuRestrictionsType type;

    /**
     * The value of restrictions. If the restriction type is set to location.
     * This would be different locations where the SKU is restricted.
     */
    @JsonProperty(value = "values", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> values;

    /**
     * The reason for restriction. Possible values include: 'QuotaId',
     * 'NotAvailableForSubscription'.
     */
    @JsonProperty(value = "reasonCode", access = JsonProperty.Access.WRITE_ONLY)
    private ResourceSkuRestrictionsReasonCode reasonCode;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public ResourceSkuRestrictionsType type() {
        return this.type;
    }

    /**
     * Get the values value.
     *
     * @return the values value
     */
    public List<String> values() {
        return this.values;
    }

    /**
     * Get the reasonCode value.
     *
     * @return the reasonCode value
     */
    public ResourceSkuRestrictionsReasonCode reasonCode() {
        return this.reasonCode;
    }

}

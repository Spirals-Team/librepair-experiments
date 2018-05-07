/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.servicebus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SKU of the namespace.
 */
public class Sku {
    /**
     * Name of this SKU. Possible values include: 'Basic', 'Standard',
     * 'Premium'.
     */
    @JsonProperty(value = "name")
    private SkuName name;

    /**
     * The billing tier of this particular SKU. Possible values include:
     * 'Basic', 'Standard', 'Premium'.
     */
    @JsonProperty(value = "tier", required = true)
    private SkuTier tier;

    /**
     * The specified messaging units for the tier. For Premium tier, capacity
     * are 1,2 and 4.
     */
    @JsonProperty(value = "capacity")
    private Integer capacity;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public SkuName name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the Sku object itself.
     */
    public Sku withName(SkuName name) {
        this.name = name;
        return this;
    }

    /**
     * Get the tier value.
     *
     * @return the tier value
     */
    public SkuTier tier() {
        return this.tier;
    }

    /**
     * Set the tier value.
     *
     * @param tier the tier value to set
     * @return the Sku object itself.
     */
    public Sku withTier(SkuTier tier) {
        this.tier = tier;
        return this;
    }

    /**
     * Get the capacity value.
     *
     * @return the capacity value
     */
    public Integer capacity() {
        return this.capacity;
    }

    /**
     * Set the capacity value.
     *
     * @param capacity the capacity value to set
     * @return the Sku object itself.
     */
    public Sku withCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

}

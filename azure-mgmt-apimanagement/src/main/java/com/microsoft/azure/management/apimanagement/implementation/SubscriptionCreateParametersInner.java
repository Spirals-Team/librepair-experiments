/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.microsoft.azure.management.apimanagement.SubscriptionState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Subscription create details.
 */
@JsonFlatten
public class SubscriptionCreateParametersInner {
    /**
     * User (user id path) for whom subscription is being created in form
     * /users/{uid}.
     */
    @JsonProperty(value = "properties.userId", required = true)
    private String userId;

    /**
     * Product (product id path) for which subscription is being created in
     * form /products/{productid}.
     */
    @JsonProperty(value = "properties.productId", required = true)
    private String productId;

    /**
     * Subscription name.
     */
    @JsonProperty(value = "properties.displayName", required = true)
    private String displayName;

    /**
     * Primary subscription key. If not specified during request key will be
     * generated automatically.
     */
    @JsonProperty(value = "properties.primaryKey")
    private String primaryKey;

    /**
     * Secondary subscription key. If not specified during request key will be
     * generated automatically.
     */
    @JsonProperty(value = "properties.secondaryKey")
    private String secondaryKey;

    /**
     * Initial subscription state. If no value is specified, subscription is
     * created with Submitted state. Possible states are * active – the
     * subscription is active, * suspended – the subscription is blocked, and
     * the subscriber cannot call any APIs of the product, * submitted – the
     * subscription request has been made by the developer, but has not yet
     * been approved or rejected, * rejected – the subscription request has
     * been denied by an administrator, * cancelled – the subscription has been
     * cancelled by the developer or administrator, * expired – the
     * subscription reached its expiration date and was deactivated. Possible
     * values include: 'suspended', 'active', 'expired', 'submitted',
     * 'rejected', 'cancelled'.
     */
    @JsonProperty(value = "properties.state")
    private SubscriptionState state;

    /**
     * Get the userId value.
     *
     * @return the userId value
     */
    public String userId() {
        return this.userId;
    }

    /**
     * Set the userId value.
     *
     * @param userId the userId value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Get the productId value.
     *
     * @return the productId value
     */
    public String productId() {
        return this.productId;
    }

    /**
     * Set the productId value.
     *
     * @param productId the productId value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the primaryKey value.
     *
     * @return the primaryKey value
     */
    public String primaryKey() {
        return this.primaryKey;
    }

    /**
     * Set the primaryKey value.
     *
     * @param primaryKey the primaryKey value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    /**
     * Get the secondaryKey value.
     *
     * @return the secondaryKey value
     */
    public String secondaryKey() {
        return this.secondaryKey;
    }

    /**
     * Set the secondaryKey value.
     *
     * @param secondaryKey the secondaryKey value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withSecondaryKey(String secondaryKey) {
        this.secondaryKey = secondaryKey;
        return this;
    }

    /**
     * Get the state value.
     *
     * @return the state value
     */
    public SubscriptionState state() {
        return this.state;
    }

    /**
     * Set the state value.
     *
     * @param state the state value to set
     * @return the SubscriptionCreateParametersInner object itself.
     */
    public SubscriptionCreateParametersInner withState(SubscriptionState state) {
        this.state = state;
        return this;
    }

}

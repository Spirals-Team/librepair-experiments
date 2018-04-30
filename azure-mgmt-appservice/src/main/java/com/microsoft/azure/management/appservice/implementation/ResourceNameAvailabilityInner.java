/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.InAvailabilityReasonType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Information regarding availbility of a resource name.
 */
public class ResourceNameAvailabilityInner {
    /**
     * &lt;code&gt;true&lt;/code&gt; indicates name is valid and available.
     * &lt;code&gt;false&lt;/code&gt; indicates the name is invalid,
     * unavailable, or both.
     */
    @JsonProperty(value = "nameAvailable")
    private Boolean nameAvailable;

    /**
     * &lt;code&gt;Invalid&lt;/code&gt; indicates the name provided does not
     * match Azure App Service naming requirements.
     * &lt;code&gt;AlreadyExists&lt;/code&gt; indicates that the name is
     * already in use and is therefore unavailable. Possible values include:
     * 'Invalid', 'AlreadyExists'.
     */
    @JsonProperty(value = "reason")
    private InAvailabilityReasonType reason;

    /**
     * If reason == invalid, provide the user with the reason why the given
     * name is invalid, and provide the resource naming requirements so that
     * the user can select a valid name. If reason == AlreadyExists, explain
     * that resource name is already in use, and direct them to select a
     * different name.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * Get the nameAvailable value.
     *
     * @return the nameAvailable value
     */
    public Boolean nameAvailable() {
        return this.nameAvailable;
    }

    /**
     * Set the nameAvailable value.
     *
     * @param nameAvailable the nameAvailable value to set
     * @return the ResourceNameAvailabilityInner object itself.
     */
    public ResourceNameAvailabilityInner withNameAvailable(Boolean nameAvailable) {
        this.nameAvailable = nameAvailable;
        return this;
    }

    /**
     * Get the reason value.
     *
     * @return the reason value
     */
    public InAvailabilityReasonType reason() {
        return this.reason;
    }

    /**
     * Set the reason value.
     *
     * @param reason the reason value to set
     * @return the ResourceNameAvailabilityInner object itself.
     */
    public ResourceNameAvailabilityInner withReason(InAvailabilityReasonType reason) {
        this.reason = reason;
        return this;
    }

    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message value.
     *
     * @param message the message value to set
     * @return the ResourceNameAvailabilityInner object itself.
     */
    public ResourceNameAvailabilityInner withMessage(String message) {
        this.message = message;
        return this;
    }

}

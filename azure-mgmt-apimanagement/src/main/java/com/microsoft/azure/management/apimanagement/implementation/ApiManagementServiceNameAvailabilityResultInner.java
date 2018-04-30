/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.microsoft.azure.management.apimanagement.NameAvailabilityReason;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response of the CheckNameAvailability operation.
 */
public class ApiManagementServiceNameAvailabilityResultInner {
    /**
     * True if the name is available and can be used to create a new API
     * Management service; otherwise false.
     */
    @JsonProperty(value = "nameAvailable", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean nameAvailable;

    /**
     * If reason == invalid, provide the user with the reason why the given
     * name is invalid, and provide the resource naming requirements so that
     * the user can select a valid name. If reason == AlreadyExists, explain
     * that &lt;resourceName&gt; is already in use, and direct them to select a
     * different name.
     */
    @JsonProperty(value = "message", access = JsonProperty.Access.WRITE_ONLY)
    private String message;

    /**
     * Invalid indicates the name provided does not match the resource
     * provider’s naming requirements (incorrect length, unsupported
     * characters, etc.)  AlreadyExists indicates that the name is already in
     * use and is therefore unavailable. Possible values include: 'Valid',
     * 'Invalid', 'AlreadyExists'.
     */
    @JsonProperty(value = "reason")
    private NameAvailabilityReason reason;

    /**
     * Get the nameAvailable value.
     *
     * @return the nameAvailable value
     */
    public Boolean nameAvailable() {
        return this.nameAvailable;
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
     * Get the reason value.
     *
     * @return the reason value
     */
    public NameAvailabilityReason reason() {
        return this.reason;
    }

    /**
     * Set the reason value.
     *
     * @param reason the reason value to set
     * @return the ApiManagementServiceNameAvailabilityResultInner object itself.
     */
    public ApiManagementServiceNameAvailabilityResultInner withReason(NameAvailabilityReason reason) {
        this.reason = reason;
        return this;
    }

}

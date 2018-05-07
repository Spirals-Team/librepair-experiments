/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error Body contract.
 */
public class ErrorResponse {
    /**
     * Service-defined error code. This code serves as a sub-status for the
     * HTTP error code specified in the response.
     */
    @JsonProperty(value = "code")
    private String code;

    /**
     * Human-readable representation of the error.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * The list of invalid fields send in request, in case of validation error.
     */
    @JsonProperty(value = "details")
    private List<ErrorFieldContract> details;

    /**
     * Get the code value.
     *
     * @return the code value
     */
    public String code() {
        return this.code;
    }

    /**
     * Set the code value.
     *
     * @param code the code value to set
     * @return the ErrorResponse object itself.
     */
    public ErrorResponse withCode(String code) {
        this.code = code;
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
     * @return the ErrorResponse object itself.
     */
    public ErrorResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the details value.
     *
     * @return the details value
     */
    public List<ErrorFieldContract> details() {
        return this.details;
    }

    /**
     * Set the details value.
     *
     * @param details the details value to set
     * @return the ErrorResponse object itself.
     */
    public ErrorResponse withDetails(List<ErrorFieldContract> details) {
        this.details = details;
        return this;
    }

}

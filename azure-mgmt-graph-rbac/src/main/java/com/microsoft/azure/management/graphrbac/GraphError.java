/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Active Directory error information.
 */
@JsonFlatten
public class GraphError {
    /**
     * Error code.
     */
    @JsonProperty(value = "odata\\.error.code")
    private String code;

    /**
     * Error message value.
     */
    @JsonProperty(value = "odata\\.error.message.value")
    private String message;

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
     * @return the GraphError object itself.
     */
    public GraphError withCode(String code) {
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
     * @return the GraphError object itself.
     */
    public GraphError withMessage(String message) {
        this.message = message;
        return this;
    }

}

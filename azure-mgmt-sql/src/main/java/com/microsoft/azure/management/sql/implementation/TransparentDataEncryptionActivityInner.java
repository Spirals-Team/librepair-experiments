/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.TransparentDataEncryptionActivityStates;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Represents a database transparent data encryption Scan.
 */
@JsonFlatten
public class TransparentDataEncryptionActivityInner extends ProxyResourceInner {
    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * The status of the database. Possible values include: 'Encrypting',
     * 'Decrypting'.
     */
    @JsonProperty(value = "properties.status", access = JsonProperty.Access.WRITE_ONLY)
    private TransparentDataEncryptionActivityStates status;

    /**
     * The percent complete of the transparent data encryption scan for a
     * database.
     */
    @JsonProperty(value = "properties.percentComplete", access = JsonProperty.Access.WRITE_ONLY)
    private Double percentComplete;

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public TransparentDataEncryptionActivityStates status() {
        return this.status;
    }

    /**
     * Get the percentComplete value.
     *
     * @return the percentComplete value
     */
    public Double percentComplete() {
        return this.percentComplete;
    }

}

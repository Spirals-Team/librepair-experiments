/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Api Release details.
 */
@JsonFlatten
public class ApiReleaseContractInner extends Resource {
    /**
     * Identifier of the API the release belongs to.
     */
    @JsonProperty(value = "properties.apiId")
    private String apiId;

    /**
     * The time the API was released. The date conforms to the following
     * format: yyyy-MM-ddTHH:mm:ssZ as specified by the ISO 8601 standard.
     */
    @JsonProperty(value = "properties.createdDateTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdDateTime;

    /**
     * The time the API release was updated.
     */
    @JsonProperty(value = "properties.updatedDateTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime updatedDateTime;

    /**
     * Release Notes.
     */
    @JsonProperty(value = "properties.notes")
    private String notes;

    /**
     * Get the apiId value.
     *
     * @return the apiId value
     */
    public String apiId() {
        return this.apiId;
    }

    /**
     * Set the apiId value.
     *
     * @param apiId the apiId value to set
     * @return the ApiReleaseContractInner object itself.
     */
    public ApiReleaseContractInner withApiId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    /**
     * Get the createdDateTime value.
     *
     * @return the createdDateTime value
     */
    public DateTime createdDateTime() {
        return this.createdDateTime;
    }

    /**
     * Get the updatedDateTime value.
     *
     * @return the updatedDateTime value
     */
    public DateTime updatedDateTime() {
        return this.updatedDateTime;
    }

    /**
     * Get the notes value.
     *
     * @return the notes value
     */
    public String notes() {
        return this.notes;
    }

    /**
     * Set the notes value.
     *
     * @param notes the notes value to set
     * @return the ApiReleaseContractInner object itself.
     */
    public ApiReleaseContractInner withNotes(String notes) {
        this.notes = notes;
        return this;
    }

}

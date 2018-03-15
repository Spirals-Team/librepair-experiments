/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.implementation;

import com.microsoft.azure.management.logic.PartnerType;
import org.joda.time.DateTime;
import com.microsoft.azure.management.logic.PartnerContent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The integration account partner.
 */
@JsonFlatten
public class IntegrationAccountPartnerInner extends Resource {
    /**
     * The partner type. Possible values include: 'NotSpecified', 'B2B'.
     */
    @JsonProperty(value = "properties.partnerType", required = true)
    private PartnerType partnerType;

    /**
     * The created time.
     */
    @JsonProperty(value = "properties.createdTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdTime;

    /**
     * The changed time.
     */
    @JsonProperty(value = "properties.changedTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime changedTime;

    /**
     * The metadata.
     */
    @JsonProperty(value = "properties.metadata")
    private Object metadata;

    /**
     * The partner content.
     */
    @JsonProperty(value = "properties.content", required = true)
    private PartnerContent content;

    /**
     * Get the partnerType value.
     *
     * @return the partnerType value
     */
    public PartnerType partnerType() {
        return this.partnerType;
    }

    /**
     * Set the partnerType value.
     *
     * @param partnerType the partnerType value to set
     * @return the IntegrationAccountPartnerInner object itself.
     */
    public IntegrationAccountPartnerInner withPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
        return this;
    }

    /**
     * Get the createdTime value.
     *
     * @return the createdTime value
     */
    public DateTime createdTime() {
        return this.createdTime;
    }

    /**
     * Get the changedTime value.
     *
     * @return the changedTime value
     */
    public DateTime changedTime() {
        return this.changedTime;
    }

    /**
     * Get the metadata value.
     *
     * @return the metadata value
     */
    public Object metadata() {
        return this.metadata;
    }

    /**
     * Set the metadata value.
     *
     * @param metadata the metadata value to set
     * @return the IntegrationAccountPartnerInner object itself.
     */
    public IntegrationAccountPartnerInner withMetadata(Object metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get the content value.
     *
     * @return the content value
     */
    public PartnerContent content() {
        return this.content;
    }

    /**
     * Set the content value.
     *
     * @param content the content value to set
     * @return the IntegrationAccountPartnerInner object itself.
     */
    public IntegrationAccountPartnerInner withContent(PartnerContent content) {
        this.content = content;
        return this;
    }

}

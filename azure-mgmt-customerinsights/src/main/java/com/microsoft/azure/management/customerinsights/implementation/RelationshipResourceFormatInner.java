/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.customerinsights.implementation;

import com.microsoft.azure.management.customerinsights.CardinalityTypes;
import java.util.Map;
import org.joda.time.DateTime;
import java.util.List;
import com.microsoft.azure.management.customerinsights.PropertyDefinition;
import com.microsoft.azure.management.customerinsights.RelationshipTypeMapping;
import com.microsoft.azure.management.customerinsights.ProvisioningStates;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.customerinsights.ProxyResource;

/**
 * The relationship resource format.
 */
@JsonFlatten
public class RelationshipResourceFormatInner extends ProxyResource {
    /**
     * The Relationship Cardinality. Possible values include: 'OneToOne',
     * 'OneToMany', 'ManyToMany'.
     */
    @JsonProperty(value = "properties.cardinality")
    private CardinalityTypes cardinality;

    /**
     * Localized display name for the Relationship.
     */
    @JsonProperty(value = "properties.displayName")
    private Map<String, String> displayName;

    /**
     * Localized descriptions for the Relationship.
     */
    @JsonProperty(value = "properties.description")
    private Map<String, String> description;

    /**
     * The expiry date time in UTC.
     */
    @JsonProperty(value = "properties.expiryDateTimeUtc")
    private DateTime expiryDateTimeUtc;

    /**
     * The properties of the Relationship.
     */
    @JsonProperty(value = "properties.fields")
    private List<PropertyDefinition> fields;

    /**
     * Optional property to be used to map fields in profile to their strong
     * ids in related profile.
     */
    @JsonProperty(value = "properties.lookupMappings")
    private List<RelationshipTypeMapping> lookupMappings;

    /**
     * Profile type.
     */
    @JsonProperty(value = "properties.profileType", required = true)
    private String profileType;

    /**
     * Provisioning state. Possible values include: 'Provisioning',
     * 'Succeeded', 'Expiring', 'Deleting', 'HumanIntervention', 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningStates provisioningState;

    /**
     * The Relationship name.
     */
    @JsonProperty(value = "properties.relationshipName", access = JsonProperty.Access.WRITE_ONLY)
    private String relationshipName;

    /**
     * Related profile being referenced.
     */
    @JsonProperty(value = "properties.relatedProfileType", required = true)
    private String relatedProfileType;

    /**
     * The relationship guid id.
     */
    @JsonProperty(value = "properties.relationshipGuidId", access = JsonProperty.Access.WRITE_ONLY)
    private String relationshipGuidId;

    /**
     * The hub name.
     */
    @JsonProperty(value = "properties.tenantId", access = JsonProperty.Access.WRITE_ONLY)
    private String tenantId;

    /**
     * Get the cardinality value.
     *
     * @return the cardinality value
     */
    public CardinalityTypes cardinality() {
        return this.cardinality;
    }

    /**
     * Set the cardinality value.
     *
     * @param cardinality the cardinality value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withCardinality(CardinalityTypes cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public Map<String, String> displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withDisplayName(Map<String, String> displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public Map<String, String> description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withDescription(Map<String, String> description) {
        this.description = description;
        return this;
    }

    /**
     * Get the expiryDateTimeUtc value.
     *
     * @return the expiryDateTimeUtc value
     */
    public DateTime expiryDateTimeUtc() {
        return this.expiryDateTimeUtc;
    }

    /**
     * Set the expiryDateTimeUtc value.
     *
     * @param expiryDateTimeUtc the expiryDateTimeUtc value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withExpiryDateTimeUtc(DateTime expiryDateTimeUtc) {
        this.expiryDateTimeUtc = expiryDateTimeUtc;
        return this;
    }

    /**
     * Get the fields value.
     *
     * @return the fields value
     */
    public List<PropertyDefinition> fields() {
        return this.fields;
    }

    /**
     * Set the fields value.
     *
     * @param fields the fields value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withFields(List<PropertyDefinition> fields) {
        this.fields = fields;
        return this;
    }

    /**
     * Get the lookupMappings value.
     *
     * @return the lookupMappings value
     */
    public List<RelationshipTypeMapping> lookupMappings() {
        return this.lookupMappings;
    }

    /**
     * Set the lookupMappings value.
     *
     * @param lookupMappings the lookupMappings value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withLookupMappings(List<RelationshipTypeMapping> lookupMappings) {
        this.lookupMappings = lookupMappings;
        return this;
    }

    /**
     * Get the profileType value.
     *
     * @return the profileType value
     */
    public String profileType() {
        return this.profileType;
    }

    /**
     * Set the profileType value.
     *
     * @param profileType the profileType value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public ProvisioningStates provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the relationshipName value.
     *
     * @return the relationshipName value
     */
    public String relationshipName() {
        return this.relationshipName;
    }

    /**
     * Get the relatedProfileType value.
     *
     * @return the relatedProfileType value
     */
    public String relatedProfileType() {
        return this.relatedProfileType;
    }

    /**
     * Set the relatedProfileType value.
     *
     * @param relatedProfileType the relatedProfileType value to set
     * @return the RelationshipResourceFormatInner object itself.
     */
    public RelationshipResourceFormatInner withRelatedProfileType(String relatedProfileType) {
        this.relatedProfileType = relatedProfileType;
        return this;
    }

    /**
     * Get the relationshipGuidId value.
     *
     * @return the relationshipGuidId value
     */
    public String relationshipGuidId() {
        return this.relationshipGuidId;
    }

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value
     */
    public String tenantId() {
        return this.tenantId;
    }

}

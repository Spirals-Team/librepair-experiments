/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.customerinsights.implementation;

import com.microsoft.azure.management.customerinsights.EntityType;
import java.util.Map;
import java.util.List;
import com.microsoft.azure.management.customerinsights.TypePropertiesMapping;
import com.microsoft.azure.management.customerinsights.ParticipantPropertyReference;
import com.microsoft.azure.management.customerinsights.ProvisioningStates;
import com.microsoft.azure.management.customerinsights.InstanceOperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.customerinsights.ProxyResource;

/**
 * The link resource format.
 */
@JsonFlatten
public class LinkResourceFormatInner extends ProxyResource {
    /**
     * The hub name.
     */
    @JsonProperty(value = "properties.tenantId", access = JsonProperty.Access.WRITE_ONLY)
    private String tenantId;

    /**
     * The link name.
     */
    @JsonProperty(value = "properties.linkName", access = JsonProperty.Access.WRITE_ONLY)
    private String linkName;

    /**
     * Type of source entity. Possible values include: 'None', 'Profile',
     * 'Interaction', 'Relationship'.
     */
    @JsonProperty(value = "properties.sourceEntityType", required = true)
    private EntityType sourceEntityType;

    /**
     * Type of target entity. Possible values include: 'None', 'Profile',
     * 'Interaction', 'Relationship'.
     */
    @JsonProperty(value = "properties.targetEntityType", required = true)
    private EntityType targetEntityType;

    /**
     * Name of the source Entity Type.
     */
    @JsonProperty(value = "properties.sourceEntityTypeName", required = true)
    private String sourceEntityTypeName;

    /**
     * Name of the target Entity Type.
     */
    @JsonProperty(value = "properties.targetEntityTypeName", required = true)
    private String targetEntityTypeName;

    /**
     * Localized display name for the Link.
     */
    @JsonProperty(value = "properties.displayName")
    private Map<String, String> displayName;

    /**
     * Localized descriptions for the Link.
     */
    @JsonProperty(value = "properties.description")
    private Map<String, String> description;

    /**
     * The set of properties mappings between the source and target Types.
     */
    @JsonProperty(value = "properties.mappings")
    private List<TypePropertiesMapping> mappings;

    /**
     * The properties that represent the participating profile.
     */
    @JsonProperty(value = "properties.participantPropertyReferences", required = true)
    private List<ParticipantPropertyReference> participantPropertyReferences;

    /**
     * Provisioning state. Possible values include: 'Provisioning',
     * 'Succeeded', 'Expiring', 'Deleting', 'HumanIntervention', 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningStates provisioningState;

    /**
     * Indicating whether the link is reference only link. This flag is ingored
     * if the Mappings are defined. If the mappings are not defined and it is
     * set to true, links processing will not create or update profiles.
     */
    @JsonProperty(value = "properties.referenceOnly")
    private Boolean referenceOnly;

    /**
     * Determines whether this link is supposed to create or delete instances
     * if Link is NOT Reference Only. Possible values include: 'Upsert',
     * 'Delete'.
     */
    @JsonProperty(value = "properties.operationType")
    private InstanceOperationType operationType;

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value
     */
    public String tenantId() {
        return this.tenantId;
    }

    /**
     * Get the linkName value.
     *
     * @return the linkName value
     */
    public String linkName() {
        return this.linkName;
    }

    /**
     * Get the sourceEntityType value.
     *
     * @return the sourceEntityType value
     */
    public EntityType sourceEntityType() {
        return this.sourceEntityType;
    }

    /**
     * Set the sourceEntityType value.
     *
     * @param sourceEntityType the sourceEntityType value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withSourceEntityType(EntityType sourceEntityType) {
        this.sourceEntityType = sourceEntityType;
        return this;
    }

    /**
     * Get the targetEntityType value.
     *
     * @return the targetEntityType value
     */
    public EntityType targetEntityType() {
        return this.targetEntityType;
    }

    /**
     * Set the targetEntityType value.
     *
     * @param targetEntityType the targetEntityType value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withTargetEntityType(EntityType targetEntityType) {
        this.targetEntityType = targetEntityType;
        return this;
    }

    /**
     * Get the sourceEntityTypeName value.
     *
     * @return the sourceEntityTypeName value
     */
    public String sourceEntityTypeName() {
        return this.sourceEntityTypeName;
    }

    /**
     * Set the sourceEntityTypeName value.
     *
     * @param sourceEntityTypeName the sourceEntityTypeName value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withSourceEntityTypeName(String sourceEntityTypeName) {
        this.sourceEntityTypeName = sourceEntityTypeName;
        return this;
    }

    /**
     * Get the targetEntityTypeName value.
     *
     * @return the targetEntityTypeName value
     */
    public String targetEntityTypeName() {
        return this.targetEntityTypeName;
    }

    /**
     * Set the targetEntityTypeName value.
     *
     * @param targetEntityTypeName the targetEntityTypeName value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withTargetEntityTypeName(String targetEntityTypeName) {
        this.targetEntityTypeName = targetEntityTypeName;
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
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withDisplayName(Map<String, String> displayName) {
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
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withDescription(Map<String, String> description) {
        this.description = description;
        return this;
    }

    /**
     * Get the mappings value.
     *
     * @return the mappings value
     */
    public List<TypePropertiesMapping> mappings() {
        return this.mappings;
    }

    /**
     * Set the mappings value.
     *
     * @param mappings the mappings value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withMappings(List<TypePropertiesMapping> mappings) {
        this.mappings = mappings;
        return this;
    }

    /**
     * Get the participantPropertyReferences value.
     *
     * @return the participantPropertyReferences value
     */
    public List<ParticipantPropertyReference> participantPropertyReferences() {
        return this.participantPropertyReferences;
    }

    /**
     * Set the participantPropertyReferences value.
     *
     * @param participantPropertyReferences the participantPropertyReferences value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withParticipantPropertyReferences(List<ParticipantPropertyReference> participantPropertyReferences) {
        this.participantPropertyReferences = participantPropertyReferences;
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
     * Get the referenceOnly value.
     *
     * @return the referenceOnly value
     */
    public Boolean referenceOnly() {
        return this.referenceOnly;
    }

    /**
     * Set the referenceOnly value.
     *
     * @param referenceOnly the referenceOnly value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withReferenceOnly(Boolean referenceOnly) {
        this.referenceOnly = referenceOnly;
        return this;
    }

    /**
     * Get the operationType value.
     *
     * @return the operationType value
     */
    public InstanceOperationType operationType() {
        return this.operationType;
    }

    /**
     * Set the operationType value.
     *
     * @param operationType the operationType value to set
     * @return the LinkResourceFormatInner object itself.
     */
    public LinkResourceFormatInner withOperationType(InstanceOperationType operationType) {
        this.operationType = operationType;
        return this;
    }

}

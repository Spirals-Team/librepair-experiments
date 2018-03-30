/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor.implementation;

import java.util.Map;
import java.util.List;
import com.microsoft.azure.management.monitor.AutoscaleProfile;
import com.microsoft.azure.management.monitor.AutoscaleNotification;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The autoscale setting object for patch operations.
 */
@JsonFlatten
public class AutoscaleSettingResourcePatchInner {
    /**
     * Resource tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * the collection of automatic scaling profiles that specify different
     * scaling parameters for different time periods. A maximum of 20 profiles
     * can be specified.
     */
    @JsonProperty(value = "properties.profiles", required = true)
    private List<AutoscaleProfile> profiles;

    /**
     * the collection of notifications.
     */
    @JsonProperty(value = "properties.notifications")
    private List<AutoscaleNotification> notifications;

    /**
     * the enabled flag. Specifies whether automatic scaling is enabled for the
     * resource. The default value is 'true'.
     */
    @JsonProperty(value = "properties.enabled")
    private Boolean enabled;

    /**
     * the name of the autoscale setting.
     */
    @JsonProperty(value = "properties.name")
    private String name;

    /**
     * the resource identifier of the resource that the autoscale setting
     * should be added to.
     */
    @JsonProperty(value = "properties.targetResourceUri")
    private String targetResourceUri;

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the profiles value.
     *
     * @return the profiles value
     */
    public List<AutoscaleProfile> profiles() {
        return this.profiles;
    }

    /**
     * Set the profiles value.
     *
     * @param profiles the profiles value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withProfiles(List<AutoscaleProfile> profiles) {
        this.profiles = profiles;
        return this;
    }

    /**
     * Get the notifications value.
     *
     * @return the notifications value
     */
    public List<AutoscaleNotification> notifications() {
        return this.notifications;
    }

    /**
     * Set the notifications value.
     *
     * @param notifications the notifications value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withNotifications(List<AutoscaleNotification> notifications) {
        this.notifications = notifications;
        return this;
    }

    /**
     * Get the enabled value.
     *
     * @return the enabled value
     */
    public Boolean enabled() {
        return this.enabled;
    }

    /**
     * Set the enabled value.
     *
     * @param enabled the enabled value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the targetResourceUri value.
     *
     * @return the targetResourceUri value
     */
    public String targetResourceUri() {
        return this.targetResourceUri;
    }

    /**
     * Set the targetResourceUri value.
     *
     * @param targetResourceUri the targetResourceUri value to set
     * @return the AutoscaleSettingResourcePatchInner object itself.
     */
    public AutoscaleSettingResourcePatchInner withTargetResourceUri(String targetResourceUri) {
        this.targetResourceUri = targetResourceUri;
        return this;
    }

}

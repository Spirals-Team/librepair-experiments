/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery.implementation;

import com.microsoft.azure.management.gallery.RuleCondition;
import java.util.List;
import com.microsoft.azure.management.gallery.RuleAction;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The alert rule resource.
 */
@JsonFlatten
public class AlertRuleResourceInner extends Resource {
    /**
     * the name of the alert rule.
     */
    @JsonProperty(value = "properties.name", required = true)
    private String alertRuleResourceName;

    /**
     * the description of the alert rule that will be included in the alert
     * email.
     */
    @JsonProperty(value = "properties.description")
    private String description;

    /**
     * the flag that indicates whether the alert rule is enabled.
     */
    @JsonProperty(value = "properties.isEnabled", required = true)
    private boolean isEnabled;

    /**
     * the condition that results in the alert rule being activated.
     */
    @JsonProperty(value = "properties.condition")
    private RuleCondition condition;

    /**
     * the array of actions that are performed when the alert rule becomes
     * active, and when an alert condition is resolved.
     */
    @JsonProperty(value = "properties.actions")
    private List<RuleAction> actions;

    /**
     * Last time the rule was updated in ISO8601 format.
     */
    @JsonProperty(value = "properties.lastUpdatedTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastUpdatedTime;

    /**
     * Get the alertRuleResourceName value.
     *
     * @return the alertRuleResourceName value
     */
    public String alertRuleResourceName() {
        return this.alertRuleResourceName;
    }

    /**
     * Set the alertRuleResourceName value.
     *
     * @param alertRuleResourceName the alertRuleResourceName value to set
     * @return the AlertRuleResourceInner object itself.
     */
    public AlertRuleResourceInner withAlertRuleResourceName(String alertRuleResourceName) {
        this.alertRuleResourceName = alertRuleResourceName;
        return this;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the AlertRuleResourceInner object itself.
     */
    public AlertRuleResourceInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the isEnabled value.
     *
     * @return the isEnabled value
     */
    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Set the isEnabled value.
     *
     * @param isEnabled the isEnabled value to set
     * @return the AlertRuleResourceInner object itself.
     */
    public AlertRuleResourceInner withIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    /**
     * Get the condition value.
     *
     * @return the condition value
     */
    public RuleCondition condition() {
        return this.condition;
    }

    /**
     * Set the condition value.
     *
     * @param condition the condition value to set
     * @return the AlertRuleResourceInner object itself.
     */
    public AlertRuleResourceInner withCondition(RuleCondition condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Get the actions value.
     *
     * @return the actions value
     */
    public List<RuleAction> actions() {
        return this.actions;
    }

    /**
     * Set the actions value.
     *
     * @param actions the actions value to set
     * @return the AlertRuleResourceInner object itself.
     */
    public AlertRuleResourceInner withActions(List<RuleAction> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * Get the lastUpdatedTime value.
     *
     * @return the lastUpdatedTime value
     */
    public DateTime lastUpdatedTime() {
        return this.lastUpdatedTime;
    }

}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor;

import org.joda.time.Period;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The parameters for the scaling action.
 */
public class ScaleAction {
    /**
     * the scale direction. Whether the scaling action increases or decreases
     * the number of instances. Possible values include: 'None', 'Increase',
     * 'Decrease'.
     */
    @JsonProperty(value = "direction", required = true)
    private ScaleDirection direction;

    /**
     * the type of action that should occur when the scale rule fires. Possible
     * values include: 'ChangeCount', 'PercentChangeCount', 'ExactCount'.
     */
    @JsonProperty(value = "type", required = true)
    private ScaleType type;

    /**
     * the number of instances that are involved in the scaling action. This
     * value must be 1 or greater. The default value is 1.
     */
    @JsonProperty(value = "value")
    private String value;

    /**
     * the amount of time to wait since the last scaling action before this
     * action occurs. It must be between 1 week and 1 minute in ISO 8601
     * format.
     */
    @JsonProperty(value = "cooldown", required = true)
    private Period cooldown;

    /**
     * Get the direction value.
     *
     * @return the direction value
     */
    public ScaleDirection direction() {
        return this.direction;
    }

    /**
     * Set the direction value.
     *
     * @param direction the direction value to set
     * @return the ScaleAction object itself.
     */
    public ScaleAction withDirection(ScaleDirection direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public ScaleType type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the ScaleAction object itself.
     */
    public ScaleAction withType(ScaleType type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the ScaleAction object itself.
     */
    public ScaleAction withValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Get the cooldown value.
     *
     * @return the cooldown value
     */
    public Period cooldown() {
        return this.cooldown;
    }

    /**
     * Set the cooldown value.
     *
     * @param cooldown the cooldown value to set
     * @return the ScaleAction object itself.
     */
    public ScaleAction withCooldown(Period cooldown) {
        this.cooldown = cooldown;
        return this;
    }

}

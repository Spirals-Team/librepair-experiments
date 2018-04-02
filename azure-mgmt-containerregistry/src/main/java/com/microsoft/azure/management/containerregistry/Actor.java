/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerregistry;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The agent that initiated the event. For most situations, this could be from
 * the authorization context of the request.
 */
public class Actor {
    /**
     * The subject or username associated with the request context that
     * generated the event.
     */
    @JsonProperty(value = "name")
    private String name;

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
     * @return the Actor object itself.
     */
    public Actor withName(String name) {
        this.name = name;
        return this;
    }

}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cognitiveservices.implementation;

import com.microsoft.azure.management.cognitiveservices.OperationDisplayInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The operation supported by Cognitive Services.
 */
public class OperationEntityInner {
    /**
     * Operation name: {provider}/{resource}/{operation}.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * The operation supported by Cognitive Services.
     */
    @JsonProperty(value = "display")
    private OperationDisplayInfo display;

    /**
     * The origin of the operation.
     */
    @JsonProperty(value = "origin")
    private String origin;

    /**
     * Additional properties.
     */
    @JsonProperty(value = "properties")
    private Object properties;

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
     * @return the OperationEntityInner object itself.
     */
    public OperationEntityInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the display value.
     *
     * @return the display value
     */
    public OperationDisplayInfo display() {
        return this.display;
    }

    /**
     * Set the display value.
     *
     * @param display the display value to set
     * @return the OperationEntityInner object itself.
     */
    public OperationEntityInner withDisplay(OperationDisplayInfo display) {
        this.display = display;
        return this;
    }

    /**
     * Get the origin value.
     *
     * @return the origin value
     */
    public String origin() {
        return this.origin;
    }

    /**
     * Set the origin value.
     *
     * @param origin the origin value to set
     * @return the OperationEntityInner object itself.
     */
    public OperationEntityInner withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public Object properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the OperationEntityInner object itself.
     */
    public OperationEntityInner withProperties(Object properties) {
        this.properties = properties;
        return this;
    }

}

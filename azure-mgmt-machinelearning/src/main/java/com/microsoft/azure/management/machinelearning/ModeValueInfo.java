/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.machinelearning;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Nested parameter definition.
 */
public class ModeValueInfo {
    /**
     * The interface string name for the nested parameter.
     */
    @JsonProperty(value = "interfaceString")
    private String interfaceString;

    /**
     * The definition of the parameter.
     */
    @JsonProperty(value = "parameters")
    private List<ModuleAssetParameter> parameters;

    /**
     * Get the interfaceString value.
     *
     * @return the interfaceString value
     */
    public String interfaceString() {
        return this.interfaceString;
    }

    /**
     * Set the interfaceString value.
     *
     * @param interfaceString the interfaceString value to set
     * @return the ModeValueInfo object itself.
     */
    public ModeValueInfo withInterfaceString(String interfaceString) {
        this.interfaceString = interfaceString;
        return this;
    }

    /**
     * Get the parameters value.
     *
     * @return the parameters value
     */
    public List<ModuleAssetParameter> parameters() {
        return this.parameters;
    }

    /**
     * Set the parameters value.
     *
     * @param parameters the parameters value to set
     * @return the ModeValueInfo object itself.
     */
    public ModeValueInfo withParameters(List<ModuleAssetParameter> parameters) {
        this.parameters = parameters;
        return this;
    }

}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batchai;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies the settings for a custom tool kit job.
 */
public class CustomToolkitSettings {
    /**
     * The command line to execute the custom toolkit Job.
     */
    @JsonProperty(value = "commandLine")
    private String commandLine;

    /**
     * Get the commandLine value.
     *
     * @return the commandLine value
     */
    public String commandLine() {
        return this.commandLine;
    }

    /**
     * Set the commandLine value.
     *
     * @param commandLine the commandLine value to set
     * @return the CustomToolkitSettings object itself.
     */
    public CustomToolkitSettings withCommandLine(String commandLine) {
        this.commandLine = commandLine;
        return this;
    }

}

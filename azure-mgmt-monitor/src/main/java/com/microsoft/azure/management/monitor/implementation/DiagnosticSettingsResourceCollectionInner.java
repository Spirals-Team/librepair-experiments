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

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a collection of alert rule resources.
 */
public class DiagnosticSettingsResourceCollectionInner {
    /**
     * The collection of diagnostic settings resources;.
     */
    @JsonProperty(value = "value")
    private List<DiagnosticSettingsResourceInner> value;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<DiagnosticSettingsResourceInner> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the DiagnosticSettingsResourceCollectionInner object itself.
     */
    public DiagnosticSettingsResourceCollectionInner withValue(List<DiagnosticSettingsResourceInner> value) {
        this.value = value;
        return this;
    }

}

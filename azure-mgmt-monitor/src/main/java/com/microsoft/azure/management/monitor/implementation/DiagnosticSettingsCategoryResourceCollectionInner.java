/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.monitor.implementation;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a collection of diagnostic setting category resources.
 */
public class DiagnosticSettingsCategoryResourceCollectionInner {
    /**
     * The collection of diagnostic settings category resources.
     */
    @JsonProperty(value = "value")
    private List<DiagnosticSettingsCategoryResourceInner> value;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<DiagnosticSettingsCategoryResourceInner> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the DiagnosticSettingsCategoryResourceCollectionInner object itself.
     */
    public DiagnosticSettingsCategoryResourceCollectionInner withValue(List<DiagnosticSettingsCategoryResourceInner> value) {
        this.value = value;
        return this;
    }

}

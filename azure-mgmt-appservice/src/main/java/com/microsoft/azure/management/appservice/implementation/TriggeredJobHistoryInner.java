/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import java.util.List;
import com.microsoft.azure.management.appservice.TriggeredJobRun;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Triggered Web Job History. List of Triggered Web Job Run Information
 * elements.
 */
@JsonFlatten
public class TriggeredJobHistoryInner extends ProxyOnlyResource {
    /**
     * List of triggered web job runs.
     */
    @JsonProperty(value = "properties.triggeredJobRuns")
    private List<TriggeredJobRun> triggeredJobRuns;

    /**
     * Get the triggeredJobRuns value.
     *
     * @return the triggeredJobRuns value
     */
    public List<TriggeredJobRun> triggeredJobRuns() {
        return this.triggeredJobRuns;
    }

    /**
     * Set the triggeredJobRuns value.
     *
     * @param triggeredJobRuns the triggeredJobRuns value to set
     * @return the TriggeredJobHistoryInner object itself.
     */
    public TriggeredJobHistoryInner withTriggeredJobRuns(List<TriggeredJobRun> triggeredJobRuns) {
        this.triggeredJobRuns = triggeredJobRuns;
        return this;
    }

}

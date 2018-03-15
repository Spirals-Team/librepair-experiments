/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains information about orchestrator.
 */
public class OrchestratorProfile {
    /**
     * Orchestrator type.
     */
    @JsonProperty(value = "orchestratorType", required = true)
    private String orchestratorType;

    /**
     * Orchestrator version (major, minor, patch).
     */
    @JsonProperty(value = "orchestratorVersion", required = true)
    private String orchestratorVersion;

    /**
     * Get the orchestratorType value.
     *
     * @return the orchestratorType value
     */
    public String orchestratorType() {
        return this.orchestratorType;
    }

    /**
     * Set the orchestratorType value.
     *
     * @param orchestratorType the orchestratorType value to set
     * @return the OrchestratorProfile object itself.
     */
    public OrchestratorProfile withOrchestratorType(String orchestratorType) {
        this.orchestratorType = orchestratorType;
        return this;
    }

    /**
     * Get the orchestratorVersion value.
     *
     * @return the orchestratorVersion value
     */
    public String orchestratorVersion() {
        return this.orchestratorVersion;
    }

    /**
     * Set the orchestratorVersion value.
     *
     * @param orchestratorVersion the orchestratorVersion value to set
     * @return the OrchestratorProfile object itself.
     */
    public OrchestratorProfile withOrchestratorVersion(String orchestratorVersion) {
        this.orchestratorVersion = orchestratorVersion;
        return this;
    }

}

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import java.util.List;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties of an artifact.
 */
public class ArtifactInstallPropertiesFragment {
    /**
     * The artifact's identifier.
     */
    @JsonProperty(value = "artifactId")
    private String artifactId;

    /**
     * The parameters of the artifact.
     */
    @JsonProperty(value = "parameters")
    private List<ArtifactParameterPropertiesFragment> parameters;

    /**
     * The status of the artifact.
     */
    @JsonProperty(value = "status")
    private String status;

    /**
     * The status message from the deployment.
     */
    @JsonProperty(value = "deploymentStatusMessage")
    private String deploymentStatusMessage;

    /**
     * The status message from the virtual machine extension.
     */
    @JsonProperty(value = "vmExtensionStatusMessage")
    private String vmExtensionStatusMessage;

    /**
     * The time that the artifact starts to install on the virtual machine.
     */
    @JsonProperty(value = "installTime")
    private DateTime installTime;

    /**
     * Get the artifactId value.
     *
     * @return the artifactId value
     */
    public String artifactId() {
        return this.artifactId;
    }

    /**
     * Set the artifactId value.
     *
     * @param artifactId the artifactId value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    /**
     * Get the parameters value.
     *
     * @return the parameters value
     */
    public List<ArtifactParameterPropertiesFragment> parameters() {
        return this.parameters;
    }

    /**
     * Set the parameters value.
     *
     * @param parameters the parameters value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withParameters(List<ArtifactParameterPropertiesFragment> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public String status() {
        return this.status;
    }

    /**
     * Set the status value.
     *
     * @param status the status value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get the deploymentStatusMessage value.
     *
     * @return the deploymentStatusMessage value
     */
    public String deploymentStatusMessage() {
        return this.deploymentStatusMessage;
    }

    /**
     * Set the deploymentStatusMessage value.
     *
     * @param deploymentStatusMessage the deploymentStatusMessage value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withDeploymentStatusMessage(String deploymentStatusMessage) {
        this.deploymentStatusMessage = deploymentStatusMessage;
        return this;
    }

    /**
     * Get the vmExtensionStatusMessage value.
     *
     * @return the vmExtensionStatusMessage value
     */
    public String vmExtensionStatusMessage() {
        return this.vmExtensionStatusMessage;
    }

    /**
     * Set the vmExtensionStatusMessage value.
     *
     * @param vmExtensionStatusMessage the vmExtensionStatusMessage value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withVmExtensionStatusMessage(String vmExtensionStatusMessage) {
        this.vmExtensionStatusMessage = vmExtensionStatusMessage;
        return this;
    }

    /**
     * Get the installTime value.
     *
     * @return the installTime value
     */
    public DateTime installTime() {
        return this.installTime;
    }

    /**
     * Set the installTime value.
     *
     * @param installTime the installTime value to set
     * @return the ArtifactInstallPropertiesFragment object itself.
     */
    public ArtifactInstallPropertiesFragment withInstallTime(DateTime installTime) {
        this.installTime = installTime;
        return this;
    }

}

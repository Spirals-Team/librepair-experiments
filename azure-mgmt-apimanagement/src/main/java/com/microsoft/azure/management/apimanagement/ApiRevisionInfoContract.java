/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object used to create an API Revision or Version based on an existing API
 * Revision.
 */
public class ApiRevisionInfoContract {
    /**
     * Resource identifier of API to be used to create the revision from.
     */
    @JsonProperty(value = "sourceApiId")
    private String sourceApiId;

    /**
     * Version identifier for the new API Version.
     */
    @JsonProperty(value = "apiVersionName")
    private String apiVersionName;

    /**
     * Description of new API Revision.
     */
    @JsonProperty(value = "apiRevisionDescription")
    private String apiRevisionDescription;

    /**
     * Version set details.
     */
    @JsonProperty(value = "apiVersionSet")
    private ApiVersionSetContractDetails apiVersionSet;

    /**
     * Get the sourceApiId value.
     *
     * @return the sourceApiId value
     */
    public String sourceApiId() {
        return this.sourceApiId;
    }

    /**
     * Set the sourceApiId value.
     *
     * @param sourceApiId the sourceApiId value to set
     * @return the ApiRevisionInfoContract object itself.
     */
    public ApiRevisionInfoContract withSourceApiId(String sourceApiId) {
        this.sourceApiId = sourceApiId;
        return this;
    }

    /**
     * Get the apiVersionName value.
     *
     * @return the apiVersionName value
     */
    public String apiVersionName() {
        return this.apiVersionName;
    }

    /**
     * Set the apiVersionName value.
     *
     * @param apiVersionName the apiVersionName value to set
     * @return the ApiRevisionInfoContract object itself.
     */
    public ApiRevisionInfoContract withApiVersionName(String apiVersionName) {
        this.apiVersionName = apiVersionName;
        return this;
    }

    /**
     * Get the apiRevisionDescription value.
     *
     * @return the apiRevisionDescription value
     */
    public String apiRevisionDescription() {
        return this.apiRevisionDescription;
    }

    /**
     * Set the apiRevisionDescription value.
     *
     * @param apiRevisionDescription the apiRevisionDescription value to set
     * @return the ApiRevisionInfoContract object itself.
     */
    public ApiRevisionInfoContract withApiRevisionDescription(String apiRevisionDescription) {
        this.apiRevisionDescription = apiRevisionDescription;
        return this;
    }

    /**
     * Get the apiVersionSet value.
     *
     * @return the apiVersionSet value
     */
    public ApiVersionSetContractDetails apiVersionSet() {
        return this.apiVersionSet;
    }

    /**
     * Set the apiVersionSet value.
     *
     * @param apiVersionSet the apiVersionSet value to set
     * @return the ApiRevisionInfoContract object itself.
     */
    public ApiRevisionInfoContract withApiVersionSet(ApiVersionSetContractDetails apiVersionSet) {
        this.apiVersionSet = apiVersionSet;
        return this;
    }

}

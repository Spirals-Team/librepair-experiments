/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cdn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Input of the custom domain to be validated for DNS mapping.
 */
public class ValidateCustomDomainInput {
    /**
     * The host name of the custom domain. Must be a domain name.
     */
    @JsonProperty(value = "hostName", required = true)
    private String hostName;

    /**
     * Get the hostName value.
     *
     * @return the hostName value
     */
    public String hostName() {
        return this.hostName;
    }

    /**
     * Set the hostName value.
     *
     * @param hostName the hostName value to set
     * @return the ValidateCustomDomainInput object itself.
     */
    public ValidateCustomDomainInput withHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

}

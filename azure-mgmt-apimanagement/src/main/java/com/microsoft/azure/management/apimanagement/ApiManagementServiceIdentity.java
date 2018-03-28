/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identity properties of the Api Management service resource.
 */
public class ApiManagementServiceIdentity {
    /**
     * The identity type. Currently the only supported type is
     * 'SystemAssigned'.
     */
    @JsonProperty(value = "type", required = true)
    private String type;

    /**
     * The principal id of the identity.
     */
    @JsonProperty(value = "principalId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID principalId;

    /**
     * The client tenant id of the identity.
     */
    @JsonProperty(value = "tenantId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID tenantId;

    /**
     * Creates an instance of ApiManagementServiceIdentity class.
     */
    public ApiManagementServiceIdentity() {
        type = "SystemAssigned";
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the ApiManagementServiceIdentity object itself.
     */
    public ApiManagementServiceIdentity withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the principalId value.
     *
     * @return the principalId value
     */
    public UUID principalId() {
        return this.principalId;
    }

    /**
     * Get the tenantId value.
     *
     * @return the tenantId value
     */
    public UUID tenantId() {
        return this.tenantId;
    }

}

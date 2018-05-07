/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.cosmosdb.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The access keys for the given database account.
 */
@JsonFlatten
public class DatabaseAccountListKeysResultInner {
    /**
     * Base 64 encoded value of the primary read-write key.
     */
    @JsonProperty(value = "primaryMasterKey", access = JsonProperty.Access.WRITE_ONLY)
    private String primaryMasterKey;

    /**
     * Base 64 encoded value of the secondary read-write key.
     */
    @JsonProperty(value = "secondaryMasterKey", access = JsonProperty.Access.WRITE_ONLY)
    private String secondaryMasterKey;

    /**
     * Base 64 encoded value of the primary read-only key.
     */
    @JsonProperty(value = "properties.primaryReadonlyMasterKey", access = JsonProperty.Access.WRITE_ONLY)
    private String primaryReadonlyMasterKey;

    /**
     * Base 64 encoded value of the secondary read-only key.
     */
    @JsonProperty(value = "properties.secondaryReadonlyMasterKey", access = JsonProperty.Access.WRITE_ONLY)
    private String secondaryReadonlyMasterKey;

    /**
     * Get the primaryMasterKey value.
     *
     * @return the primaryMasterKey value
     */
    public String primaryMasterKey() {
        return this.primaryMasterKey;
    }

    /**
     * Get the secondaryMasterKey value.
     *
     * @return the secondaryMasterKey value
     */
    public String secondaryMasterKey() {
        return this.secondaryMasterKey;
    }

    /**
     * Get the primaryReadonlyMasterKey value.
     *
     * @return the primaryReadonlyMasterKey value
     */
    public String primaryReadonlyMasterKey() {
        return this.primaryReadonlyMasterKey;
    }

    /**
     * Get the secondaryReadonlyMasterKey value.
     *
     * @return the secondaryReadonlyMasterKey value
     */
    public String secondaryReadonlyMasterKey() {
        return this.secondaryReadonlyMasterKey;
    }

}

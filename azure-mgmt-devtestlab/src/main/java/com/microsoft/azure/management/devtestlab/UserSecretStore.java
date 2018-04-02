/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.devtestlab;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties of a user's secret store.
 */
public class UserSecretStore {
    /**
     * The URI of the user's Key vault.
     */
    @JsonProperty(value = "keyVaultUri")
    private String keyVaultUri;

    /**
     * The ID of the user's Key vault.
     */
    @JsonProperty(value = "keyVaultId")
    private String keyVaultId;

    /**
     * Get the keyVaultUri value.
     *
     * @return the keyVaultUri value
     */
    public String keyVaultUri() {
        return this.keyVaultUri;
    }

    /**
     * Set the keyVaultUri value.
     *
     * @param keyVaultUri the keyVaultUri value to set
     * @return the UserSecretStore object itself.
     */
    public UserSecretStore withKeyVaultUri(String keyVaultUri) {
        this.keyVaultUri = keyVaultUri;
        return this;
    }

    /**
     * Get the keyVaultId value.
     *
     * @return the keyVaultId value
     */
    public String keyVaultId() {
        return this.keyVaultId;
    }

    /**
     * Set the keyVaultId value.
     *
     * @param keyVaultId the keyVaultId value to set
     * @return the UserSecretStore object itself.
     */
    public UserSecretStore withKeyVaultId(String keyVaultId) {
        this.keyVaultId = keyVaultId;
        return this;
    }

}

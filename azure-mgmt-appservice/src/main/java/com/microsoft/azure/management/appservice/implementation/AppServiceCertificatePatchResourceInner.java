/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.KeyVaultSecretStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Key Vault container ARM resource for a certificate that is purchased through
 * Azure.
 */
@JsonFlatten
public class AppServiceCertificatePatchResourceInner extends ProxyOnlyResource {
    /**
     * Key Vault resource Id.
     */
    @JsonProperty(value = "properties.keyVaultId")
    private String keyVaultId;

    /**
     * Key Vault secret name.
     */
    @JsonProperty(value = "properties.keyVaultSecretName")
    private String keyVaultSecretName;

    /**
     * Status of the Key Vault secret. Possible values include: 'Initialized',
     * 'WaitingOnCertificateOrder', 'Succeeded', 'CertificateOrderFailed',
     * 'OperationNotPermittedOnKeyVault',
     * 'AzureServiceUnauthorizedToAccessKeyVault', 'KeyVaultDoesNotExist',
     * 'KeyVaultSecretDoesNotExist', 'UnknownError', 'ExternalPrivateKey',
     * 'Unknown'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private KeyVaultSecretStatus provisioningState;

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
     * @return the AppServiceCertificatePatchResourceInner object itself.
     */
    public AppServiceCertificatePatchResourceInner withKeyVaultId(String keyVaultId) {
        this.keyVaultId = keyVaultId;
        return this;
    }

    /**
     * Get the keyVaultSecretName value.
     *
     * @return the keyVaultSecretName value
     */
    public String keyVaultSecretName() {
        return this.keyVaultSecretName;
    }

    /**
     * Set the keyVaultSecretName value.
     *
     * @param keyVaultSecretName the keyVaultSecretName value to set
     * @return the AppServiceCertificatePatchResourceInner object itself.
     */
    public AppServiceCertificatePatchResourceInner withKeyVaultSecretName(String keyVaultSecretName) {
        this.keyVaultSecretName = keyVaultSecretName;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public KeyVaultSecretStatus provisioningState() {
        return this.provisioningState;
    }

}

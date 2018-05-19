/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.implementation;

import org.joda.time.DateTime;
import com.microsoft.azure.management.logic.KeyVaultKeyReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The integration account certificate.
 */
@JsonFlatten
public class IntegrationAccountCertificateInner extends Resource {
    /**
     * The created time.
     */
    @JsonProperty(value = "properties.createdTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdTime;

    /**
     * The changed time.
     */
    @JsonProperty(value = "properties.changedTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime changedTime;

    /**
     * The metadata.
     */
    @JsonProperty(value = "properties.metadata")
    private Object metadata;

    /**
     * The key details in the key vault.
     */
    @JsonProperty(value = "properties.key")
    private KeyVaultKeyReference key;

    /**
     * The public certificate.
     */
    @JsonProperty(value = "properties.publicCertificate")
    private String publicCertificate;

    /**
     * Get the createdTime value.
     *
     * @return the createdTime value
     */
    public DateTime createdTime() {
        return this.createdTime;
    }

    /**
     * Get the changedTime value.
     *
     * @return the changedTime value
     */
    public DateTime changedTime() {
        return this.changedTime;
    }

    /**
     * Get the metadata value.
     *
     * @return the metadata value
     */
    public Object metadata() {
        return this.metadata;
    }

    /**
     * Set the metadata value.
     *
     * @param metadata the metadata value to set
     * @return the IntegrationAccountCertificateInner object itself.
     */
    public IntegrationAccountCertificateInner withMetadata(Object metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get the key value.
     *
     * @return the key value
     */
    public KeyVaultKeyReference key() {
        return this.key;
    }

    /**
     * Set the key value.
     *
     * @param key the key value to set
     * @return the IntegrationAccountCertificateInner object itself.
     */
    public IntegrationAccountCertificateInner withKey(KeyVaultKeyReference key) {
        this.key = key;
        return this;
    }

    /**
     * Get the publicCertificate value.
     *
     * @return the publicCertificate value
     */
    public String publicCertificate() {
        return this.publicCertificate;
    }

    /**
     * Set the publicCertificate value.
     *
     * @param publicCertificate the publicCertificate value to set
     * @return the IntegrationAccountCertificateInner object itself.
     */
    public IntegrationAccountCertificateInner withPublicCertificate(String publicCertificate) {
        this.publicCertificate = publicCertificate;
        return this;
    }

}

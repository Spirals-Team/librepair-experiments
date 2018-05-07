/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.PublicCertificateLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Public certificate object.
 */
@JsonFlatten
public class PublicCertificateInner extends ProxyOnlyResource {
    /**
     * Public Certificate byte array.
     */
    @JsonProperty(value = "properties.blob")
    private byte[] blob;

    /**
     * Public Certificate Location. Possible values include: 'CurrentUserMy',
     * 'LocalMachineMy', 'Unknown'.
     */
    @JsonProperty(value = "properties.publicCertificateLocation")
    private PublicCertificateLocation publicCertificateLocation;

    /**
     * Certificate Thumbprint.
     */
    @JsonProperty(value = "properties.thumbprint", access = JsonProperty.Access.WRITE_ONLY)
    private String thumbprint;

    /**
     * Get the blob value.
     *
     * @return the blob value
     */
    public byte[] blob() {
        return this.blob;
    }

    /**
     * Set the blob value.
     *
     * @param blob the blob value to set
     * @return the PublicCertificateInner object itself.
     */
    public PublicCertificateInner withBlob(byte[] blob) {
        this.blob = blob;
        return this;
    }

    /**
     * Get the publicCertificateLocation value.
     *
     * @return the publicCertificateLocation value
     */
    public PublicCertificateLocation publicCertificateLocation() {
        return this.publicCertificateLocation;
    }

    /**
     * Set the publicCertificateLocation value.
     *
     * @param publicCertificateLocation the publicCertificateLocation value to set
     * @return the PublicCertificateInner object itself.
     */
    public PublicCertificateInner withPublicCertificateLocation(PublicCertificateLocation publicCertificateLocation) {
        this.publicCertificateLocation = publicCertificateLocation;
        return this;
    }

    /**
     * Get the thumbprint value.
     *
     * @return the thumbprint value
     */
    public String thumbprint() {
        return this.thumbprint;
    }

}

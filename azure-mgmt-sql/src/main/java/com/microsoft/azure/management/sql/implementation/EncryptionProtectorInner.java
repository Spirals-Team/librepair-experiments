/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.ServerKeyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The server encryption protector.
 */
@JsonFlatten
public class EncryptionProtectorInner extends ProxyResourceInner {
    /**
     * Kind of encryption protector. This is metadata used for the Azure portal
     * experience.
     */
    @JsonProperty(value = "kind")
    private String kind;

    /**
     * Resource location.
     */
    @JsonProperty(value = "location", access = JsonProperty.Access.WRITE_ONLY)
    private String location;

    /**
     * Subregion of the encryption protector.
     */
    @JsonProperty(value = "properties.subregion", access = JsonProperty.Access.WRITE_ONLY)
    private String subregion;

    /**
     * The name of the server key.
     */
    @JsonProperty(value = "properties.serverKeyName")
    private String serverKeyName;

    /**
     * The encryption protector type like 'ServiceManaged', 'AzureKeyVault'.
     * Possible values include: 'ServiceManaged', 'AzureKeyVault'.
     */
    @JsonProperty(value = "properties.serverKeyType", required = true)
    private ServerKeyType serverKeyType;

    /**
     * The URI of the server key.
     */
    @JsonProperty(value = "properties.uri", access = JsonProperty.Access.WRITE_ONLY)
    private String uri;

    /**
     * Thumbprint of the server key.
     */
    @JsonProperty(value = "properties.thumbprint", access = JsonProperty.Access.WRITE_ONLY)
    private String thumbprint;

    /**
     * Get the kind value.
     *
     * @return the kind value
     */
    public String kind() {
        return this.kind;
    }

    /**
     * Set the kind value.
     *
     * @param kind the kind value to set
     * @return the EncryptionProtectorInner object itself.
     */
    public EncryptionProtectorInner withKind(String kind) {
        this.kind = kind;
        return this;
    }

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Get the subregion value.
     *
     * @return the subregion value
     */
    public String subregion() {
        return this.subregion;
    }

    /**
     * Get the serverKeyName value.
     *
     * @return the serverKeyName value
     */
    public String serverKeyName() {
        return this.serverKeyName;
    }

    /**
     * Set the serverKeyName value.
     *
     * @param serverKeyName the serverKeyName value to set
     * @return the EncryptionProtectorInner object itself.
     */
    public EncryptionProtectorInner withServerKeyName(String serverKeyName) {
        this.serverKeyName = serverKeyName;
        return this;
    }

    /**
     * Get the serverKeyType value.
     *
     * @return the serverKeyType value
     */
    public ServerKeyType serverKeyType() {
        return this.serverKeyType;
    }

    /**
     * Set the serverKeyType value.
     *
     * @param serverKeyType the serverKeyType value to set
     * @return the EncryptionProtectorInner object itself.
     */
    public EncryptionProtectorInner withServerKeyType(ServerKeyType serverKeyType) {
        this.serverKeyType = serverKeyType;
        return this;
    }

    /**
     * Get the uri value.
     *
     * @return the uri value
     */
    public String uri() {
        return this.uri;
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

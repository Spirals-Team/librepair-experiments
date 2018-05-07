/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.FailoverGroupReadWriteEndpoint;
import com.microsoft.azure.management.sql.FailoverGroupReadOnlyEndpoint;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * A failover group update request.
 */
@JsonFlatten
public class FailoverGroupUpdateInner {
    /**
     * Read-write endpoint of the failover group instance.
     */
    @JsonProperty(value = "properties.readWriteEndpoint")
    private FailoverGroupReadWriteEndpoint readWriteEndpoint;

    /**
     * Read-only endpoint of the failover group instance.
     */
    @JsonProperty(value = "properties.readOnlyEndpoint")
    private FailoverGroupReadOnlyEndpoint readOnlyEndpoint;

    /**
     * List of databases in the failover group.
     */
    @JsonProperty(value = "properties.databases")
    private List<String> databases;

    /**
     * Resource tags.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * Get the readWriteEndpoint value.
     *
     * @return the readWriteEndpoint value
     */
    public FailoverGroupReadWriteEndpoint readWriteEndpoint() {
        return this.readWriteEndpoint;
    }

    /**
     * Set the readWriteEndpoint value.
     *
     * @param readWriteEndpoint the readWriteEndpoint value to set
     * @return the FailoverGroupUpdateInner object itself.
     */
    public FailoverGroupUpdateInner withReadWriteEndpoint(FailoverGroupReadWriteEndpoint readWriteEndpoint) {
        this.readWriteEndpoint = readWriteEndpoint;
        return this;
    }

    /**
     * Get the readOnlyEndpoint value.
     *
     * @return the readOnlyEndpoint value
     */
    public FailoverGroupReadOnlyEndpoint readOnlyEndpoint() {
        return this.readOnlyEndpoint;
    }

    /**
     * Set the readOnlyEndpoint value.
     *
     * @param readOnlyEndpoint the readOnlyEndpoint value to set
     * @return the FailoverGroupUpdateInner object itself.
     */
    public FailoverGroupUpdateInner withReadOnlyEndpoint(FailoverGroupReadOnlyEndpoint readOnlyEndpoint) {
        this.readOnlyEndpoint = readOnlyEndpoint;
        return this;
    }

    /**
     * Get the databases value.
     *
     * @return the databases value
     */
    public List<String> databases() {
        return this.databases;
    }

    /**
     * Set the databases value.
     *
     * @param databases the databases value to set
     * @return the FailoverGroupUpdateInner object itself.
     */
    public FailoverGroupUpdateInner withDatabases(List<String> databases) {
        this.databases = databases;
        return this;
    }

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set
     * @return the FailoverGroupUpdateInner object itself.
     */
    public FailoverGroupUpdateInner withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}

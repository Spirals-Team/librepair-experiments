/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.streamanalytics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Describes a DocumentDB data source.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("Microsoft.Storage/DocumentDB")
@JsonFlatten
public class DocumentDbOutputDataSource extends OutputDataSource {
    /**
     * The DocumentDB account name or ID. Required on PUT (CreateOrReplace)
     * requests.
     */
    @JsonProperty(value = "properties.accountId")
    private String accountId;

    /**
     * The account key for the DocumentDB account. Required on PUT
     * (CreateOrReplace) requests.
     */
    @JsonProperty(value = "properties.accountKey")
    private String accountKey;

    /**
     * The name of the DocumentDB database. Required on PUT (CreateOrReplace)
     * requests.
     */
    @JsonProperty(value = "properties.database")
    private String database;

    /**
     * The collection name pattern for the collections to be used. The
     * collection name format can be constructed using the optional {partition}
     * token, where partitions start from 0. See the DocumentDB section of
     * https://docs.microsoft.com/en-us/rest/api/streamanalytics/stream-analytics-output
     * for more information. Required on PUT (CreateOrReplace) requests.
     */
    @JsonProperty(value = "properties.collectionNamePattern")
    private String collectionNamePattern;

    /**
     * The name of the field in output events used to specify the key for
     * partitioning output across collections. If 'collectionNamePattern'
     * contains the {partition} token, this property is required to be
     * specified.
     */
    @JsonProperty(value = "properties.partitionKey")
    private String partitionKey;

    /**
     * The name of the field in output events used to specify the primary key
     * which insert or update operations are based on.
     */
    @JsonProperty(value = "properties.documentId")
    private String documentId;

    /**
     * Get the accountId value.
     *
     * @return the accountId value
     */
    public String accountId() {
        return this.accountId;
    }

    /**
     * Set the accountId value.
     *
     * @param accountId the accountId value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * Get the accountKey value.
     *
     * @return the accountKey value
     */
    public String accountKey() {
        return this.accountKey;
    }

    /**
     * Set the accountKey value.
     *
     * @param accountKey the accountKey value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withAccountKey(String accountKey) {
        this.accountKey = accountKey;
        return this;
    }

    /**
     * Get the database value.
     *
     * @return the database value
     */
    public String database() {
        return this.database;
    }

    /**
     * Set the database value.
     *
     * @param database the database value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withDatabase(String database) {
        this.database = database;
        return this;
    }

    /**
     * Get the collectionNamePattern value.
     *
     * @return the collectionNamePattern value
     */
    public String collectionNamePattern() {
        return this.collectionNamePattern;
    }

    /**
     * Set the collectionNamePattern value.
     *
     * @param collectionNamePattern the collectionNamePattern value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withCollectionNamePattern(String collectionNamePattern) {
        this.collectionNamePattern = collectionNamePattern;
        return this;
    }

    /**
     * Get the partitionKey value.
     *
     * @return the partitionKey value
     */
    public String partitionKey() {
        return this.partitionKey;
    }

    /**
     * Set the partitionKey value.
     *
     * @param partitionKey the partitionKey value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
        return this;
    }

    /**
     * Get the documentId value.
     *
     * @return the documentId value
     */
    public String documentId() {
        return this.documentId;
    }

    /**
     * Set the documentId value.
     *
     * @param documentId the documentId value to set
     * @return the DocumentDbOutputDataSource object itself.
     */
    public DocumentDbOutputDataSource withDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

}

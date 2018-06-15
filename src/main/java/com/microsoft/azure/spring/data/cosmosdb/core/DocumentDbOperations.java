/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.data.cosmosdb.core;

import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.PartitionKey;
import com.microsoft.azure.spring.data.cosmosdb.core.convert.MappingDocumentDbConverter;
import com.microsoft.azure.spring.data.cosmosdb.core.query.Query;
import com.microsoft.azure.spring.data.cosmosdb.repository.support.DocumentDbEntityInformation;

import java.util.List;

public interface DocumentDbOperations {

    String getCollectionName(Class<?> entityClass);

    DocumentCollection createCollectionIfNotExists(DocumentDbEntityInformation information,
                                                   String partitionKeyFieldName);

    <T> List<T> findAll(Class<T> entityClass);

    <T> List<T> findAll(String collectionName, Class<T> entityClass);

    <T> T findById(Object id,
                   Class<T> entityClass);

    <T> T findById(String collectionName,
                   Object id,
                   Class<T> entityClass);

    <T> List<T> find(Query query,
                     Class<T> entityClass,
                     String collectionName);

    <T> T insert(T objectToSave, PartitionKey partitionKey);

    <T> T insert(String collectionName,
                 T objectToSave,
                 PartitionKey partitionKey);

    <T> void upsert(T object, PartitionKey partitionKey);

    <T> void upsert(String collectionName,
                    T object,
                    PartitionKey partitionKey);

    <T> void deleteById(String collectionName,
                        Object id,
                        PartitionKey partitionKey);

    void deleteAll(String collectionName);

    <T> List<T> delete(Query query, Class<T> entityClass, String collectionName);

    MappingDocumentDbConverter getConverter();
}

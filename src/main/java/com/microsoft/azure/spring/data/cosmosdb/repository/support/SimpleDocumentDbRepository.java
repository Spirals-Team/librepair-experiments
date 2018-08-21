/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.data.cosmosdb.repository.support;


import com.microsoft.azure.documentdb.PartitionKey;
import com.microsoft.azure.spring.data.cosmosdb.core.DocumentDbOperations;
import com.microsoft.azure.spring.data.cosmosdb.core.query.Criteria;
import com.microsoft.azure.spring.data.cosmosdb.core.query.CriteriaType;
import com.microsoft.azure.spring.data.cosmosdb.core.query.DocumentQuery;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleDocumentDbRepository<T, ID extends Serializable> implements DocumentDbRepository<T, ID> {

    private final DocumentDbOperations documentDbOperations;
    private final DocumentDbEntityInformation<T, ID> information;

    public SimpleDocumentDbRepository(DocumentDbEntityInformation<T, ID> metadata,
                                      ApplicationContext applicationContext) {
        this.documentDbOperations = applicationContext.getBean(DocumentDbOperations.class);
        this.information = metadata;
    }

    public SimpleDocumentDbRepository(DocumentDbEntityInformation<T, ID> metadata,
                                      DocumentDbOperations dbOperations) {
        this.documentDbOperations = dbOperations;
        this.information = metadata;
    }

    /**
     * save entity without partition
     *
     * @param entity to be saved
     * @param <S>
     * @return entity
     */
    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "entity must not be null");

        // create collection if not exists
        documentDbOperations.createCollectionIfNotExists(this.information,
                this.information.getPartitionKeyFieldName());

        // save entity
        if (information.isNew(entity)) {
            return documentDbOperations.insert(information.getCollectionName(),
                    entity,
                    createKey(information.getPartitionKeyFieldValue(entity)));
        } else {
            documentDbOperations.upsert(information.getCollectionName(),
                    entity, createKey(information.getPartitionKeyFieldValue(entity)));
        }

        return entity;
    }

    private PartitionKey createKey(String partitionKeyValue) {
        if (StringUtils.isEmpty(partitionKeyValue)) {
            return null;
        }

        return new PartitionKey(partitionKeyValue);
    }

    /**
     * batch save entities
     *
     * @param entities
     * @param <S>
     * @return
     */
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "Iterable entities should not be null");

        // create collection if not exists
        documentDbOperations.createCollectionIfNotExists(this.information,
                this.information.getPartitionKeyFieldName());

        for (final S entity : entities) {
            save(entity);
        }

        return entities;
    }

    /**
     * find all entities from one collection without configuring partition key value
     *
     * @return
     */
    @Override
    public Iterable<T> findAll() {
        return documentDbOperations.findAll(information.getCollectionName(), information.getJavaType());
    }

    /**
     * find entities based on id list from one collection without partitions
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        Assert.notNull(ids, "Iterable ids should not be null");

        final List<T> entities = new ArrayList<T>();

        for (final ID id : ids) {
            final Optional<T> entity = findById(id);

            if (entity.isPresent()) {
                entities.add(entity.get());
            }
        }
        return entities;
    }

    /**
     * find one entity per id without partitions
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        Assert.notNull(id, "id must not be null");

        if (id instanceof String && !StringUtils.hasText((String) id)) {
            return Optional.empty();
        }

        final T result = documentDbOperations.findById(
                information.getCollectionName(), id, information.getJavaType());

        return result == null ? Optional.empty() : Optional.of(result);
    }

    /**
     * return count of documents in one collection without partitions
     *
     * @return
     */
    @Override
    public long count() {
        return documentDbOperations.count(information.getCollectionName());
    }

    /**
     * delete one document per id without configuring partition key value
     *
     * @param id
     */
    @Override
    public void deleteById(ID id) {
        Assert.notNull(id, "id to be deleted should not be null");

        documentDbOperations.deleteById(information.getCollectionName(), id, null);
    }

    /**
     * delete one document per entity
     *
     * @param entity
     */
    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity to be deleted should not be null");

        final String paritionKeyValue = information.getPartitionKeyFieldValue(entity);

        documentDbOperations.deleteById(information.getCollectionName(),
                information.getId(entity),
                paritionKeyValue == null ? null : new PartitionKey(paritionKeyValue));
    }

    /**
     * delete an collection
     */
    @Override
    public void deleteAll() {
        documentDbOperations.deleteAll(information.getCollectionName());
    }

    /**
     * delete list of entities without partitions
     *
     * @param entities
     */
    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "Iterable entities should not be null");

        for (final T entity : entities) {
            delete(entity);
        }
    }

    /**
     * check if an entity exists per id without partition
     *
     * @param primaryKey
     * @return
     */
    @Override
    public boolean existsById(ID primaryKey) {
        Assert.notNull(primaryKey, "primaryKey should not be null");

        return findById(primaryKey).isPresent();
    }

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    @Override
    public Iterable<T> findAll(@NonNull Sort sort) {
        Assert.notNull(sort, "sort of findAll should not be null");
        final DocumentQuery query = new DocumentQuery(Criteria.getInstance(CriteriaType.ALL), sort);

        return documentDbOperations.find(query, information.getJavaType(), information.getCollectionName());
    }

    /**
     * FindQuerySpecGenerator
     * Returns a Page of entities meeting the paging restriction provided in the Pageable object.
     *
     * @param pageable
     * @return a page of entities
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        Assert.notNull(pageable, "pageable should not be null");

        return documentDbOperations.findAll(pageable, information.getJavaType(), information.getCollectionName());
    }
}

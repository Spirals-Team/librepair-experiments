package com.cmpl.web.core.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.cmpl.web.core.models.BaseEntity;

/**
 * Interface commune de repository
 * 
 * @author Louis
 *
 * @param <T>
 */
public interface BaseRepository<T extends BaseEntity> extends QuerydslPredicateExecutor<T>, JpaRepository<T, Long> {

}

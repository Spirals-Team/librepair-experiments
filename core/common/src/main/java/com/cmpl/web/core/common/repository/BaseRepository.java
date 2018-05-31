package com.cmpl.web.core.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * Interface commune de repository
 * 
 * @author Louis
 *
 * @param <T>
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}

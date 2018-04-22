package com.acqua.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.Department;

/**
 * Repository for manage the {@link Department} entity
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface DepartmentRepository extends MongoRepository<Department, String> {
	

}

package com.acqua.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.Employee;

/**
 * Repository for manage the {@link Employee} entity
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	

}

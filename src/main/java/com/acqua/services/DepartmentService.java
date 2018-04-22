package com.acqua.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acqua.entities.Department;
import com.acqua.repositories.DepartmentRepository;



/**
 * Service layer for {@link Department} object managment
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Service
public class DepartmentService {
	
	
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	
	
	/**
	 * Function that save the element passed as a parameter
	 * 
	 * @param element {@link Department} to save
	 */
	public void save(Department department) {
		departmentRepository.save(department);
	}
	
	
	
	/**
	 * Function that find element by its id
	 * 
	 * @param id {@link String} unique id
	 * 
	 * @return {@link Department} if exist, <code>null</code> otherwise
	 */
	public Department findById(String id) {
		
		Department result = null;
		Optional<Department> element = departmentRepository.findById(id);
		
		if (element.isPresent())
			result = element.get();
		
		return result;
	}
	
	
	
	/**
	 * Procedure for delete an element
	 * 
	 * @param element {@link Department} to delete
	 */
	public void delete(Department department) {
		departmentRepository.delete(department);
	}

	
	
	/**
	 * Function for list all {@link Department} stored in database
	 * 
	 * @return {@link List<Department>} response
	 */
	public List<Department> list() {
		return departmentRepository.findAll();
	}
}

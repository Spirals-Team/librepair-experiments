package com.rexam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rexam.model.TeachingUnit;

public interface TeachingUnitRepository extends CrudRepository<TeachingUnit, String>{

	/*
	 * @return la liste de tous les disciplines
	 */
	@Query(value = "select distinct discipline from TeachingUnit")
	public List<String> findDisciplines();
	
	
	public List<TeachingUnit> findAllByOrderByDisciplineAsc(); 
	
	/*
	 * se traduit en sql par LIKE %searchTerm%
	 */
	public List<TeachingUnit> findByNameIgnoreCaseContaining(String searchTerm);
	
}

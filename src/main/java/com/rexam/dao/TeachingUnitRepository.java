package com.rexam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	@Query(value = "select tu.* from teaching_Unit tu where LOWER(tu.name) LIKE %:searchTerm% or LOWER(tu.discipline) LIKE %:searchTerm% order by tu.discipline ASC",nativeQuery = true)
	public List<TeachingUnit> findDistinctByDisciplineOrName(@Param("searchTerm") String searchTerm);
	
	@Query(value = "select tu.* from teaching_Unit tu, teaching_unit_components tuc where tu.code = tuc.teaching_unit_code and tuc.components_id= ?1",nativeQuery = true)
	public List<TeachingUnit> findByComponent(Integer component);

}

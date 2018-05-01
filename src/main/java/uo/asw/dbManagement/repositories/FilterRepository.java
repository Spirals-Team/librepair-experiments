package uo.asw.dbManagement.repositories;

import org.springframework.data.repository.CrudRepository;

import uo.asw.dbManagement.model.Filter;

public interface FilterRepository extends CrudRepository<Filter, Long>{
	
	Filter findById(Long id);
}

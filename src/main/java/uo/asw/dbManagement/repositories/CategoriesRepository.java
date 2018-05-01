package uo.asw.dbManagement.repositories;

import org.springframework.data.repository.CrudRepository;

import uo.asw.dbManagement.model.Category;

public interface CategoriesRepository extends CrudRepository<Category, Long>{
	
}

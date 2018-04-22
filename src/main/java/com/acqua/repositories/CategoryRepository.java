package com.acqua.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.Category;




/**
 * Repository for {@link Category} management
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface CategoryRepository extends MongoRepository<Category, String> {

	
	
	/**
	 * Function to get {@link Category} by its name
	 * 
	 * @param name {@link String} provided name to search element
	 * 
	 * @return {@link List<Category>} list of categories, 
	 *         <code>null</code> if there aren't any elements with provided name
	 */
	List<Category> findByName(String name);
	
	
	/**
	 * Function to get {@link Category} by its unique id
	 * 
	 * @param id {@link String} unique id
	 * 
	 * @return {@link Optional<Category>}
	 */
	Optional<Category> findById(String id);
}

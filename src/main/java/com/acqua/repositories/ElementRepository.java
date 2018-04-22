package com.acqua.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.Element;



/**
 * Repository for {@link Element} managment
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface ElementRepository extends MongoRepository<Element, String> {

	
	
	/**
	 * Function to get {@link Element} by its name
	 * 
	 * @param name {@link String} provided name to search element
	 * 
	 * @return {@link List<Element>} list of elements, 
	 *         <code>null</code> if there aren't any elements with provided name
	 */
	List<Element> findByName(String name);
	
	
	/**
	 * Function to get {@link Element} by its unique id
	 * 
	 * @param id {@link String} unique id
	 * 
	 * @return {@link Optional<Element>}
	 */
	Optional<Element> findById(String id);
}

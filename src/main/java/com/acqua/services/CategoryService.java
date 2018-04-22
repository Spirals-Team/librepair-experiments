package com.acqua.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acqua.entities.Category;
import com.acqua.entities.Element;
import com.acqua.repositories.CategoryRepository;



/**
 * Service layer for {@link Category} object managment
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Service
public class CategoryService {
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	
	/**
	 * Function that save the category passed as a parameter
	 * 
	 * @param element {@link Category} to save
	 */
	public void save(Category category) {
		categoryRepository.save(category);
	}
	
	
	
	/**
	 * Function that find category by its id
	 * 
	 * @param id {@link String} unique id
	 * 
	 * @return {@link Category} if exist, <code>null</code> otherwise
	 */
	public Category findById(String id) {
		
		Category result = null;
		Optional<Category> category = categoryRepository.findById(id);
		
		if (category.isPresent())
			result = category.get();
		
		return result;
	}
	
	
	
	/**
	 * Procedure for delete a category
	 * 
	 * @param category {@link Category} to delete
	 */
	public void delete(Category category) {
		categoryRepository.delete(category);
	}

	
	
	/**
	 * Function for list all {@link Category} stored in database
	 * 
	 * @return {@link List<Category>} response
	 */
	public List<Category> list() {
		return categoryRepository.findAll();
	}
	
	
	
	/**
	 * Function to associate an {@link Element} to a container object 
	 * 
	 * @param id {@link String} unique identifier of container object
	 * 
	 * @param element {@link Element} object to associate to a container
	 * 
	 * @return {@link Category} updated container
	 */
	public Category associate(String id, Element element) {
		Category result = null;
		Optional<Category> category = categoryRepository.findById(id);
		
		if (category.isPresent()) {
			result = associate(category.get(), element);
		}
		
		return result;
	}
	
	
	
	/**
	 * Function to associate an {@link Element} to a {@link Category} object 
	 * 
	 * @param category {@link Category} container object
	 * 
	 * @param element {@link Element} object to associate to a container
	 * 
	 * @return {@link Category} updated container
	 */
	public Category associate(Category category, Element element) {
		Category result = category;
		
		List<Element> elements = result.getElements();
		
		if (CollectionUtils.isNotEmpty(elements)) {
			elements.add(element);
		} else {
			elements = Arrays.asList(element);
		}
		
		return result;
	}
}

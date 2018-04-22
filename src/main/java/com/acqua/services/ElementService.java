package com.acqua.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acqua.entities.Element;
import com.acqua.repositories.ElementRepository;



/**
 * Service layer for {@link Element} object managment
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Service
public class ElementService {
	
	
	
	@Autowired
	private ElementRepository elementRepository;
	
	
	
	
	/**
	 * Function that save the element passed as a parameter
	 * 
	 * @param element {@link Element} to save
	 */
	public void save(Element element) {
		elementRepository.save(element);
	}
	
	
	
	/**
	 * Function that find element by its id
	 * 
	 * @param id {@link String} unique id
	 * 
	 * @return {@link Element} if exist, <code>null</code> otherwise
	 */
	public Element findById(String id) {
		
		Element result = null;
		Optional<Element> element = elementRepository.findById(id);
		
		if (element.isPresent())
			result = element.get();
		
		return result;
	}
	
	
	
	/**
	 * Procedure for delete an element
	 * 
	 * @param element {@link Element} to delete
	 */
	public void delete(Element element) {
		elementRepository.delete(element);
	}

	
	
	/**
	 * Function for list all {@link Element} stored in database
	 * 
	 * @return {@link List<Element>} response
	 */
	public List<Element> list() {
		return elementRepository.findAll();
	}
}

package com.acqua.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * Entity that represents the category of skills.
 * For example: Program Language, Project Management, etc etc...
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Document(collection = "categories")
public class Category {

	@Id
	private String id;

	@NotNull
	private String name;

	private List<Element> elements;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

}

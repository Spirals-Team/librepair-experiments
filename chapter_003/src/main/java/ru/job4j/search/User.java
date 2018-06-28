package ru.job4j.search;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class User {
	private int id;
	private String name;
	private String city;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCity() {
		return this.city;
	}

}
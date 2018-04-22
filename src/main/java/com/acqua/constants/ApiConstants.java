package com.acqua.constants;

import com.acqua.entities.Department;
import com.acqua.entities.Element;

/**
 * Centralized constants only for API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class ApiConstants {
	
	
	/**
	 * Base context root name for API
	 */
	public static final String API_CONTEXT_ROOT = "/api";
	
	
	
	/**
	 * Version number of API
	 */
	public static final String API_VERSION_1 = "/v1";
	
	
	
	/**
	 * Path for {@link Element} resource
	 */
	public static final String API_PATH_ELEMENT = "/element";
	
	
	
	/**
	 * Path for {@link Category} resource
	 */
	public static final String API_PATH_CATEGORY = "/category";
	
	
	
	/**
	 * Path for {@link Employee} resource
	 */
	public static final String API_PATH_EMPLOYEE = "/employee";
	
	
	
	/**
	 * Path for {@link Department} resource
	 */
	public static final String API_PATH_DEPARTMENT = "/department";

	
	
	/**
	 * Path for {@link Skill} resource
	 */
	public static final String API_PATH_SKILL = "/skill";

	
	
	/**
	 * Path for {@link User} resource
	 */
	public static final String API_PATH_USER = "/user";
	
	
	/**
	 * Full path for {@link Element} resource v.1
	 */
	public static final String API_V1_ELEMENT = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_ELEMENT;
	
	
	
	/**
	 * Full path for {@link Category} resource v.1
	 */
	public static final String API_V1_CATEGORY = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_CATEGORY;
	
	
	
	/**
	 * Full path for {@link Employee} resource v.1
	 */
	public static final String API_V1_EMPLOYEE = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_EMPLOYEE;
	
	
	
	/**
	 * Full path for {@link Department} resource v.1
	 */
	public static final String API_V1_DEPARTMENT = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_DEPARTMENT;

	
	
	/**
	 * Full path for {@link Skill} resource v.1
	 */
	public static final String API_V1_SKILL = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_SKILL;

	
	
	/**
	 * Full path for {@link User} resource v.1
	 */
	public static final String API_V1_USER = API_CONTEXT_ROOT + API_VERSION_1 + API_PATH_USER;
	
	
	/**
	 * Protector from the constructor
	 */
	private ApiConstants() {
		throw new IllegalAccessError("Constants class");
	}

}

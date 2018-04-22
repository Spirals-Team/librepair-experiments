package com.acqua.constants;



/**
 * Centralized constants only for paths
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class PathConstants {
	
	
	/*
	 *  
	 *     C R U D   P A T H S
	 * 
	 */
	
	
	
	/**
	 * Create path
	 */
	public static final String CREATE = "/add";
	
	
	
	/**
	 * Read path
	 */
	public static final String READ   = "/get";
	
	
	
	/**
	 * Update path
	 */
	public static final String UPDATE = "/update";
	
	
	
	/**
	 * Delete path
	 */
	public static final String DELETE = "/delete";
	
	
	
	
	/*
	 *  
	 *     C U S T O M   P A T H S
	 * 
	 */
	
	
	
	/**
	 * List path
	 */
	public static final String LIST = "/list";
	
	
	/**
	 * Associate path
	 */
	public static final String ASSOCIATE = "/assoc";
	
	
	/**
	 * Associate extended path
	 */
	public static final String ASSOCIATE_EXT = "/assoc/ext";
	
	
	
	
	
	
	/**
	 * Protector from the constructor
	 */
	private PathConstants() {
		throw new IllegalAccessError("Constants class");
	}

}

/*
 * AbstractModel.java
 *
 * Created on 06 May 2006, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * editor.
 */

package framework.mvc.model;

import java.util.HashMap;

/**
 * AbstractModel Class.
 */
public abstract class AbstractModel extends java.util.Observable {

	private final HashMap<String, String> hashMap;

	/**
	 * Instantiates a new abstract model.
	 */
	public AbstractModel() {
		this.hashMap = new HashMap<String, String>();
	}

	/**
	 * Update.
	 *
	 * model
	 */
	protected void update(final String key, final String value) {
		this.hashMap.put(key, value);
	}

	/**
	 * value for key
	 *
	 * key
	 * value
	 * string
	 */
	protected String remove(final String key) {
		return this.hashMap.remove(key);
	}

	/**
	 * Gets the.
	 *
	 * key
	 * string
	 */
	protected String get(final String key) {
		return this.hashMap.remove(key);
	}

}

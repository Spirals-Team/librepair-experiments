package ru.job4j.tracker.dao;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public interface ITracker {
	/**
	 * to add a current item to a store
	 * @param item an item for adding
	 * @return true - if an item added to a store, else - false
	 */
	Item add(Item item);

	/**
	 * to update an item which exists in a store
	 * @param id an id wanted item
	 * @param item a new item which will modify wanted item
	 * @return a modified item
	 */
	void update(String id, Item item);

	/**
	 * to delete an item from a store
	 * @param id an id of item for deleting
	 */
	void delete(String id);

	/**
	 * to get all the items which exist in a store
	 * @return a list of items if there are items with the name,
	 * otherwise null
	 */
	List<Item> findAll();

	/**
	 * to find an item by its name
	 * @param key a name of a wanted item
	 * @return a list of items if there are items with the name,
	 * otherwise null
	 */
	List<Item> findByName(String key);

	/**
	 * to find an item by its id
	 * @param id an id of a wanted item
	 * @return a list of items if there are items with the id,
	 * otherwise null
	 */
	Item findById(String id);

	/**
	 * to write comment to the item with current id
	 * @param id an id of item where will be written a comment
	 * @param comment
	 */
	void writeComment(String id, String comment);
}

package ru.job4j.jdbc.tracker.dao;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public interface IItemDao {
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
	Item update(int id, Item item);

	/**
	 * to delete an item from a store
	 * @param id an id of item for deleting
	 */
	void delete(int id);

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
	Item findById(int id);

	/**
	 * to write comment to the item with current id
	 * @param id an id of item where will be written a comment
	 * @param comment
	 */
	void writeComment(int id, String comment);
}

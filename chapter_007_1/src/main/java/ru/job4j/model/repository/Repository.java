package ru.job4j.model.repository;

import ru.job4j.model.pojo.User;
import ru.job4j.model.repository.queries.Specification;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public interface Repository<T> {
	/**
	 * Adds a user to the db
	 * @param user a user being added
	 * @return true - if a user has added,
	 * otherwise - false
	 */
	boolean add(T user);

	/**
	 * Updates a current user in a db
	 * @param user a user being updated
	 * @return true - if a user has updated,
	 * otherwise - false
	 */
	boolean update(T user);

	/**
	 * Deletes a user from a db
	 * @param id an id of user being deleted
	 * @return true - if a user has deleted,
	 * otherwise - false
	 */
	boolean delete(int id);

	/**
	 * Common db's query
	 * @param specification {@link Specification} for
	 * making a condition of a query
	 * @return <code>List</code> of the users which
	 * satisfy the query
	 */
	List<User> query(Specification specification);
}

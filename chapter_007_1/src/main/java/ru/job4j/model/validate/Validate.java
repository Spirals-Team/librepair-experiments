package ru.job4j.model.validate;

import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;

import java.util.List;

/**
 * This interface allow some method for validation
 * of a store's date and working with a user's store
 * @author Yury Matskevich
 */
public interface Validate {
	/**
	 * Adds a user to the store if the store does not contain
	 * user with an id equals to the user being added
	 * @param user an added user
	 * @return true if user has been added to the store, otherwise - false
	 */
	boolean add(User user);

	/**
	 * Updates a user if it exists in the store
	 * @param user an updated user
	 * @return true if user has been updated in the store, otherwise - false
	 */
	boolean update(User user);

	/**
	 * Deletes a user from the store
	 * @param id an id of user being aded
	 * @return true if user has been deleted from the store, otherwise - false
	 */
	boolean delete(int id);

	/**
	 * Gets all users who are in the store
	 * @return {@link List} of useres if there
	 * are users in the store, otherwise it will be
	 * an empty {@link List}
	 */
	List<User> findAll();

	/**
	 * Gets a user with a current id
	 * @param id an id of the search user
	 * @return instance of {@link User} if there is
	 * user with a current id, otherwise - null
	 */
	User findById(int id);

	/**
	 * Gives all the users with current <code>Address</code>
	 * @param country a current country
	 * @param city a current city
	 * @return <code>List</code> of {@link User} if there is
	 * user with a current address, otherwise - empty <code>List</code>
	 */
	List<User> findByAddress(String country, String city);

	/**
	 * Gives all the users with current <code>Role</code>
	 * @param role a current role
	 * @return <code>List</code> of {@link User} if there is
	 * user with a current address, otherwise - empty <code>List</code>
	 */
	List<User> findByRole(Roles role);

	/**
	 * Gives all the users with current <code>Music</code>
	 * @param music a current music type
	 * @return <code>List</code> of {@link User} if there is
	 * user with a current music type, otherwise - empty <code>List</code>
	 */
	List<User> findByMusic(MusicType music);

	/**
	 * Checks existince of a user with current login and password
	 * in the store
	 * @param login a current login
	 * @param password a curent password
	 * @return user with current login and password
	 * is existed, otherwise - null
	 */
	User isCredential(String login, String password);
}

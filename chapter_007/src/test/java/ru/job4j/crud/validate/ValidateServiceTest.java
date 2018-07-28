package ru.job4j.crud.validate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.store.Store;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public abstract class ValidateServiceTest {
	private final Store store = getStore();
	private final Validate validate = getValidate();
	private User user;

	/**
	 * Adds a new user
	 */
	@Before
	public void setUp() {
		user = new User("user", "login", "email", 1L, "pass", 2, 1);
		store.add(user);
	}

	/**
	 * Deletes all users from the store
	 */
	@After
	public void backDown() {
		List<User> users = store.findAll();
		if (!users.isEmpty()) {
			for (User user : users) {
				store.delete(user.getId());
			}
		}
	}

	@Test
	public void whenUserDoesNotHaveNameThenItWillNotPassValidation() {
		User user1 = new User(null, "login1", "email1", 1L, "pass", 2, 1);
		assertFalse(validate.add(user1));
	}

	@Test
	public void whenUserDoesNotHaveLoginThenItWillNotPassValidation() {
		User user1 = new User("name1", null, "email1", 1L, "pass", 2, 1);
		assertFalse(validate.add(user1));
	}

	@Test
	public void whenUserDoesNotHaveEmailThenItWillNotNotPassValidation() {
		User user1 = new User("name1", "login1", null, 1L, "pass", 2, 1);
		assertFalse(validate.add(user1));
	}

	@Test
	public void whenUserHasLoginWhichExistsAlreadyInStoreThenItWillNotPassValidation() {
		User user1 = new User("name1", "login", "email1", 1L, "pass", 2, 1);
		assertFalse(validate.add(user1));
	}

	@Test
	public void whenUserHasEmailWhichExistsAlreadyInStoreThenItWillNotPassValidation() {
		User user1 = new User("name1", "login1", "email", 1L, "pass", 2, 1);
		assertFalse(validate.add(user1));
	}

	@Test
	public void whenUserPassAllTheChecksOfAddedMethodThenItPassValidation() {
		User user1 = new User("name1", "login1", "email1", 1L, "pass", 2, 1);
		assertTrue(validate.add(user1));
	}

	@Test
	public void whenTryToUpdateUserWhichDoesNotExistInStoreThenFalse() {
		int id = 2; //not existent id
		User user1 = new User(id, "name1", "login1", "email1", "pass", 2, 1);
		assertFalse(validate.update(user1));
	}

	@Test
	public void whenUserPassAllTheChecksOfUpdateMethodThenItPassValidation() {
		int id = store.findAll().get(0).getId(); //there is a user with a such id
		User user1 = new User(id, "name1", "login1", "email1", "pass", 2, 1);
		assertTrue(validate.update(user1));
	}

	@Test
	public void whenTryToDeleteUserByIdWhichDoesNotExistInStoreThenFalse() {
		int id = 2; //not existent id
		assertFalse(validate.delete(id));
	}

	@Test
	public void whenUserPassAllTheChecksOfDeleteMethodThenItPassValidation() {
		int id = store.findAll().get(0).getId(); //there is a user with a such id
		assertTrue(validate.delete(id));
	}

	@Test
	public void findAllTest() {
		Set<User> expectedUsers = new HashSet<>(Arrays.asList(user));
		Set<User> actualUsers = new HashSet<>(validate.findAll());
		assertEquals(expectedUsers, actualUsers);
	}

	@Test
	public void findByIdTest() {
		User curUser = store.findAll().get(0); //there is one user in the store
		assertEquals(user, validate.findById(curUser.getId()));
	}

	protected abstract Validate getValidate();
	protected abstract Store getStore();
}

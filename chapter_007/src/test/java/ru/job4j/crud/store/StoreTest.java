package ru.job4j.crud.store;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.crud.pojo.User;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public abstract class StoreTest {
	private final Store store = getStore();
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

	private int getIdFirstUser() {
		return store.findAll().get(0).getId();
	}

	@Test
	public void addTest() {
		assertEquals(user, store.findAll().get(0));
	}

	@Test
	public void deleteTest() {
		int id = store.findAll().get(0).getId();
		assertTrue(store.delete(id));
		assertNull(store.findById(id));
	}

	@Test
	public void updateTest() {
		String alterName = "alterUser";
		String alterLogin = "alterLogin";
		String alterEmil = "alterEmail";
		String alterPass = "alterPass";
		int alterCity = 2;
		int alterRole = 2;
		int id = getIdFirstUser();
		User alterUser = new User(id, alterName, alterLogin, alterEmil, alterPass, alterRole, alterCity);
		assertTrue(store.update(alterUser));
		User userFromStore = store.findById(id);
		assertEquals(alterName, userFromStore.getName());
		assertEquals(alterLogin, userFromStore.getLogin());
		assertEquals(alterEmil, userFromStore.getEmail());
	}

	@Test
	public void findAllTest() {
		User user1 =  new User("user1", "login1", "email1", 2L, "pass", 2, 2);
		store.add(user1);
		Set<User> expectedUsers = new HashSet<>(Arrays.asList(user, user1));
		Set<User> actualUsers = new HashSet<>(store.findAll());
		assertEquals(expectedUsers, actualUsers);
	}

	@Test
	public void findByIdTest() {
		User curUser = store.findAll().get(0); //there is one user in the store
		assertEquals(user, store.findById(curUser.getId()));
	}

	@Test
	public void getLoginsTest() {
//		User user1 =  new User("user1", "login1", "email1", 2L, "pass", 2);
//		store.add(user1);
//		Set<String> expectedLogins = new HashSet<>(
//				Arrays.asList(user.getLogin(), user1.getLogin(), "admin")
//		);
//		Set<String> actualLogins = new HashSet<>(
//				Arrays.asList(
//						store.findAll().get(0).getLogin(),
//						store.findAll().get(1).getLogin(),
//						store.findAll().get(2).getLogin()
//				)
//		);
//		assertEquals(expectedLogins, actualLogins);
	}

	@Test
	public void getEmailsTest() {
		User user1 =  new User("user1", "login1", "email1", 2L, "pass", 2, 1);
		store.add(user1);
		Set<String> expectedLogins = new HashSet<>(
				Arrays.asList(user.getEmail(), user1.getEmail())
		);
		Set<String> actualLogins = new HashSet<>(
				Arrays.asList(
						store.findAll().get(0).getEmail(),
						store.findAll().get(1).getEmail()
				)
		);
		assertEquals(expectedLogins, actualLogins);
	}

	protected abstract Store getStore();
}

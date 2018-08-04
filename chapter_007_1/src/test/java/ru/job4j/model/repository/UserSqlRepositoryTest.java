package ru.job4j.model.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.pojo.Address;
import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;
import ru.job4j.model.repository.queries.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public class UserSqlRepositoryTest {
	private final UserSqlRepository store = UserSqlRepository.getInstance();
	private User user = new User(
			"login",
			"password",
			"name",
			new Address("Belarus", "Brest"),
			Roles.USER,
			Arrays.asList(MusicType.RAP, MusicType.FOLK)
	);

	static  {
		CreateDb create = new CreateDb();
		create.createDb();
		create.filltebles();
	}

	@Before
	public void setUp() {
		store.add(user);
	}

	@After
	//delets all users from db
	public void backDown() {
		List<User> users = store.query(
				new UsersFromStoreSqlSpecification()
		);
		for (User item : users) {
			store.delete(item.getId());
		}
	}

	/**
	 * Returns one user from set of users from db
	 *
	 * @return <code>User</code> from a db
	 */
	private User getFirstUserFromList(Specification specification) {
		List<User> users = store.query(specification);
		return users.get(0);
	}

	@Test
	public void addNewInEmptyDb() {
		User user = getFirstUserFromList(new UsersFromStoreSqlSpecification());
		assertEquals(this.user, user);
	}

	@Test
	public void whenTryToAddNewUserWithLoginWhichAlreadyIsExistedInDbThenUserWillNotAddToTheDb() {
		User newUser = new User(
				"login",
				"password1",
				"name1",
				new Address("Belarus", "Brest"),
				Roles.USER,
				Arrays.asList(MusicType.RAP, MusicType.FOLK)
		);
		assertFalse(store.add(newUser));
		assertEquals(this.user, getFirstUserFromList(new UsersFromStoreSqlSpecification()));
	}

	@Test
	public void updateUserWithValidData() {
		User alterUser = new User(
				"alterLogin",
				"alterPassword",
				"alterName",
				new Address("Russia", "Moscow"),
				Roles.USER,
				Arrays.asList(MusicType.FOLK)
		);
		alterUser.setId(getFirstUserFromList(new UsersFromStoreSqlSpecification()).getId());
		assertTrue(store.update(alterUser));
		assertEquals(alterUser, getFirstUserFromList(new UsersFromStoreSqlSpecification()));
	}

	@Test
	public void deleteUserTest() {
		assertTrue(store.delete(getFirstUserFromList(new UsersFromStoreSqlSpecification()).getId()));
		assertTrue(store.query(new UsersFromStoreSqlSpecification()).isEmpty());
	}

	@Test
	public void findAllUsersTest() {
		User oneMoreUser = new User(
				"login1",
				"password1",
				"name1",
				new Address("Belarus", "Brest"),
				Roles.USER,
				Arrays.asList(MusicType.ROCK, MusicType.CLASSIC)
		);
		store.add(oneMoreUser);
		List<User> users = store.query(new UsersFromStoreSqlSpecification());
		Set<User> expected = new HashSet<>(Arrays.asList(user, oneMoreUser));
		Set<User> actual = new HashSet<>(users);
		assertEquals(expected, actual);
	}

	@Test
	public void findById() {
		int id = getFirstUserFromList(new UsersFromStoreSqlSpecification()).getId();
		assertEquals(store.query(new UserByIdSqlSpecification(id)).get(0), user);
	}

	@Test
	public void findUserByAddress() {
		User user = store.query(
				new UsersByAddressSqlSpecification("Belarus", "Brest")
		).get(0);
		assertEquals(this.user, user);
	}

	@Test
	public void findUserByMusicType() {
		User user = store.query(
				new UsersByMusicTypeSqlSpecification(MusicType.FOLK)
		).get(0);
		assertEquals(this.user, user);
	}

	@Test
	public void findUserByRole() {
		User user = store.query(
				new UsersByRoleSqlSpecification(Roles.USER)
		).get(0);
		assertEquals(this.user, user);
	}
}

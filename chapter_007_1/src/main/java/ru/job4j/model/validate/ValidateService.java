package ru.job4j.model.validate;

import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;
import ru.job4j.model.repository.Repository;
import ru.job4j.model.repository.UserSqlRepository;
import ru.job4j.model.repository.queries.*;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public class ValidateService implements Validate {
	private final Repository<User> store = UserSqlRepository.getInstance();
	private static ValidateService uniqueInstance = new ValidateService();

	private ValidateService() {

	}

	public static ValidateService getInstance() {
		return uniqueInstance;
	}

	@Override
	public boolean add(User user) {
		return store.add(user);
	}

	@Override
	public boolean update(User user) {
		return store.update(user);
	}

	@Override
	public boolean delete(int id) {
		return store.delete(id);
	}

	@Override
	public List<User> findAll() {
		return store.query(new UsersFromStoreSqlSpecification());
	}

	@Override
	public User findById(int id) {
		return findByCondition(new UserByIdSqlSpecification(id));
	}

	@Override
	public User isCredential(String login, String password) {
		return findByCondition(
				new UserByLoginAndPasswordSqlSpecification(
						login, password
				)
		);
	}

	@Override
	public List<User> findByAddress(String country, String city) {
		return store.query(new UsersByAddressSqlSpecification(country, city));
	}

	@Override
	public List<User> findByRole(Roles role) {
		return store.query(new UsersByRoleSqlSpecification(role));
	}

	@Override
	public List<User> findByMusic(MusicType music) {
		return store.query(new UsersByMusicTypeSqlSpecification(music));
	}

	/**
	 * A common method for finding according a current
	 * condition
	 *
	 * @param specification a condition of searching
	 * @return <code>User</code> if there is User which
	 * satisfies of condition, otherwise - null
	 */
	private User findByCondition(Specification specification) {
		List<User> users = store.query(specification);
		return users.isEmpty() ? null : users.get(0);
	}
}

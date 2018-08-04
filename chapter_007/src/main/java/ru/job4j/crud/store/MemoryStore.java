package ru.job4j.crud.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class stores user in the {@link Map}
 * @author Yury Matskevich
 */
@ThreadSafe
public class MemoryStore implements Store {
	private static final Logger LOG = Logger.getLogger(MemoryStore.class);
	private static MemoryStore uniqueInstance =
			new MemoryStore();
	private final Map<Integer, User> store =
			new ConcurrentHashMap<>();

	private MemoryStore() {
		add(new User(0, "admin", "admin", "admin@gmail.com", 0, "admin", 1, 1));
	}

	/**
	 * Creates an unique instance of {@link MemoryStore}
	 * @return an instance of {@link MemoryStore}
	 */
	public static MemoryStore getInstance() {
		return uniqueInstance;
	}

	@Override
	public boolean add(User user) {
		return store.putIfAbsent(user.getId(), user) == null;
	}

	@Override
	public boolean update(User user) {
		return store.compute(
				user.getId(),
				(k, v) ->
						setUpUserFields(
								user,
								v.getName(),
								v.getLogin(),
								v.getEmail(),
								v.getCreateDate()
						)
		) != null;
	}

	@Override
	public boolean delete(int id) {
		return store.remove(id) != null;
	}

	@Override
	public synchronized List<User> findAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public User findById(int id) {
		return store.get(id);
	}

	@Override
	public User findByLogin(String login) {
		User user = null;
		List<User> users = new ArrayList<>(store.values());
		for (User item : users) {
			if (item.getLogin().equalsIgnoreCase(login)) {
				user = item;
				break;
			}
		}
		return user;
	}

	@Override
	public List<String> getLogins() {
		List<String> logins = new ArrayList<>();
		for (Map.Entry<Integer, User> user : store.entrySet()) {
			logins.add(user.getValue().getLogin());
		}
		return logins;
	}

	@Override
	public List<String> getEmails() {
		List<String> emails = new ArrayList<>();
		for (Map.Entry<Integer, User> user : store.entrySet()) {
			emails.add(user.getValue().getEmail());
		}
		return emails;
	}

	/**
	 * Sets up all(or some) the field of a current user
	 * @param user   a current user
	 * @param name   a name to be set to a corresponding user's field
	 * @param login  a login to be set to a corresponding user's field
	 * @param email  an email to be set to a corresponding user's field
	 * @param create a create date to be set to a corresponding user's field
	 */
	private User setUpUserFields(
			User user, String name, String login, String email, long create) {
		user.setName(user.getName() == null ? name : user.getName());
		user.setLogin(user.getLogin() == null ? login : user.getLogin());
		user.setEmail(user.getEmail() == null ? email : user.getEmail());
		user.setCreateDate(create);
		return user;
	}
}

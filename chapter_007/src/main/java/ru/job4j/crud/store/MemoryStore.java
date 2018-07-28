package ru.job4j.crud.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.User;

import java.util.*;

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
			Collections.synchronizedMap(new HashMap<>());
	//a list of logins in the store
	private List<String> logins =
			Collections.synchronizedList(new ArrayList<>());
	//a list of emails in the store
	private List<String> emails =
			Collections.synchronizedList(new ArrayList<>());

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
		addLoginAndEmailToList(user);
		user.setId(getUniqueKey()); //put in a new unique id in user which it is being added
		return store.put(user.getId(), user) == null;
	}

	@Override
	public boolean update(User user) {
		int curId = user.getId();
		User curUser = findById(curId);
		setUpUserFields(
				user,
				curUser.getName(),
				curUser.getLogin(),
				curUser.getEmail(),
				curUser.getCreateDate()
		);
		deleteLoginAndEmailFromList(curUser);
		addLoginAndEmailToList(user);
		return store.replace(curId, user).equals(curUser);
	}

	@Override
	public boolean delete(int id) {
		User curUser = findById(id);
		deleteLoginAndEmailFromList(curUser);
		return store.remove(id).equals(curUser);
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
		return new ArrayList<>(logins);
	}

	@Override
	public List<String> getEmails() {
		return new ArrayList<>(emails);
	}

	/**
	 * Returns a unique id for a new user
	 * @return a value of the unigue id
	 * for a user is being added to the store
	 */
	private int getUniqueKey() {
		//returns a max key from store. If store is empty - 0
		int key = store.isEmpty() ? 0 : Collections.max(
				store.entrySet(), Map.Entry.comparingByKey()).getKey();
		key++;
		return key;
	}

	/**
	 * Sets up all(or some) the field of a current user
	 * @param user a current user
	 * @param name a name to be set to a corresponding user's field
	 * @param login a login to be set to a corresponding user's field
	 * @param email an email to be set to a corresponding user's field
	 * @param create a create date to be set to a corresponding user's field
	 */
	private void setUpUserFields(
			User user, String name, String login, String email, long create) {
		user.setName(user.getName() == null ? name : user.getName());
		user.setLogin(user.getLogin() == null ? login : user.getLogin());
		user.setEmail(user.getEmail() == null ? email : user.getEmail());
		user.setCreateDate(create);
	}

	/**
	 * Puts in a either login and/or an email in to a
	 * corresponding list if they are not there
	 * @param user a user with current login and email
	 */
	private void addLoginAndEmailToList(User user) {
		String login = user.getLogin();
		String email = user.getEmail();
		if (!logins.contains(login)) {
			logins.add(login);
		}
		if (!emails.contains(email)) {
			emails.add(email);
		}
	}

	/**
	 * Deletes a login and an email of current user
	 * from corresponding lists
	 * @param user a user with current login and email which
	 * are being deleted from the lists
	 */
	private void deleteLoginAndEmailFromList(User user) {
		logins.remove(user.getLogin());
		emails.remove(user.getEmail());
	}
}

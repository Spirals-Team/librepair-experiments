package ru.job4j.crud.validate;

import org.apache.log4j.Logger;
import ru.job4j.crud.pojo.User;
import ru.job4j.crud.store.DbStore;
import ru.job4j.crud.store.Store;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public class ValidateService implements Validate {
	private static final Logger LOG = Logger.getLogger(ValidateService.class);
	private final Store store = DbStore.getInstance();
	private static ValidateService uniqueInstance =
			new ValidateService();

	private ValidateService() {

	}

	public static ValidateService getInstance() {
		return uniqueInstance;
	}

	@Override
	public boolean add(User user) {
		boolean result = false;
		if (!thereAreFieldsWithNull(user)
				&& (vacantEmail(user.getEmail()) & vacantLogin(user.getLogin()))) {
			result = store.add(user);
		}
		return result;
	}

	@Override
	public boolean update(User user) {
		boolean result = false;
		User updateUser = findById(user.getId());
		if (updateUser != null && checkForUpdate(user, updateUser)) {
			if (user.getRole() == null) {
				user.setRole(updateUser.getRole());
			}
			result = store.update(user);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;
		if (findById(id) != null) {
			result = store.delete(id);
		}
		return result;
	}

	@Override
	public List<User> findAll() {
		return store.findAll();
	}

	@Override
	public User findById(int id) {
		return store.findById(id);
	}

	@Override
	public Integer isCredential(String login, String password) {
		Integer result = null;
		User user = findByLogin(login);
		if (user != null) {
			result = user.getPassword().equalsIgnoreCase(password)
					? user.getId()
					: null;
		}
		return result;
	}

	@Override
	public User findByLogin(String login) {
		return store.findByLogin(login);
	}

	/**
	 * Checks if a current user doesn't have
	 * a name or a login or a email or a password or a role
	 * or a city id
	 *
	 * @param user a current user
	 * @return true if user
	 */
	private boolean thereAreFieldsWithNull(User user) {
		return user.getName() == null
				|| user.getLogin() == null
				|| user.getEmail() == null
				|| user.getPassword() == null
				|| user.getRole() == null
				|| user.getCityId() == null;
	}

	/**
	 * Checks if a curent login there isn't in the store
	 *
	 * @param login a current login
	 * @return true if the login doesn't exist in
	 * the store, otherwise - false
	 */
	private boolean vacantLogin(String login) {
		boolean result = true;
		for (String item : store.getLogins()) {
			if (item.equalsIgnoreCase(login)) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * Checks if a curent email there isn't in the store
	 *
	 * @param email a current email
	 * @return true if the email doesn't exist in
	 * the store, otherwise - false
	 */
	private boolean vacantEmail(String email) {
		boolean result = true;
		for (String item : store.getEmails()) {
			if (item.equalsIgnoreCase(email)) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * Compares the equality of the fields of a logins for two users
	 *
	 * @param user1 a first user for comparing
	 * @param user2 a second user for comparing
	 * @return true if logins of two users are
	 * egual, otherwise - false
	 */
	private boolean areLoginsEqualed(User user1, User user2) {
		String login1 = user1.getLogin();
		String login2 = user2.getLogin();
		return login1.equalsIgnoreCase(login2);
	}

	/**
	 * Compares the equality of the fields of a emails for two users
	 *
	 * @param user1 a first user for comparing
	 * @param user2 a second user for comparing
	 * @return true if emails of two users are
	 * egual, otherwise - false
	 */
	private boolean areEmailsEqualed(User user1, User user2) {
		String email1 = user1.getEmail();
		String email2 = user2.getEmail();
		return email1.equalsIgnoreCase(email2);
	}

	private boolean checkForUpdate(User newUser, User oldUSer) {
		boolean equalLogins = areLoginsEqualed(newUser, oldUSer);
		boolean equalEmails = areEmailsEqualed(newUser, oldUSer);
		boolean vacantLogin = vacantLogin(newUser.getLogin());
		boolean vacantEmail = vacantEmail(newUser.getEmail());
		return (equalLogins & equalEmails) //nothing has been chanched
				|| (vacantLogin & equalEmails) //a login has chanched and an email is the same
				|| (equalLogins & vacantEmail) //a login is the same an email has chanched
				|| (vacantLogin & vacantEmail); //a login and an email have chanched
	}
}

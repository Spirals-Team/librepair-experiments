package poe.spring.delegate;

public class LoginCreationDelegate {

	public static final int MIN_LOGIN_LENGTH = 4;
	public static final int MAX_LOGIN_LENGTH = 10;
	protected static final String[] FORBIDDEN_LOGIN = { "toto", "tata", "titi" };

	public static Boolean isSizeValid(String login) {
		Boolean isValid;
		isValid = login.length() >= MIN_LOGIN_LENGTH && login.length() <= MAX_LOGIN_LENGTH;
		return isValid;
	}

	public static Boolean testForbiddenString(String login) {
		Boolean isAuthorized = true;
		for (int i = 0; (i < FORBIDDEN_LOGIN.length) && isAuthorized; i++) {
			if (FORBIDDEN_LOGIN[i].equals(login)) {
				isAuthorized = false;
			}
		}
		return isAuthorized;
	}

}

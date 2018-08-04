package ru.job4j.model.repository.queries;

/**
 * @author Yury Matskevich
 */
public class UserByLoginAndPasswordSqlSpecification implements SqlSpecification {
	private String login;
	private String password;

	public UserByLoginAndPasswordSqlSpecification(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public String toSqlQuery() {
		return String.format(
				"WHERE login_u = '%s' AND password_u = '%s'",
				login,
				password
		);
	}
}

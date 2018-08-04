package ru.job4j.model.repository.queries;

/**
 * @author Yury Matskevich
 */
public class UserByLoginSqlSpecification implements SqlSpecification {
	private String login;

	public UserByLoginSqlSpecification(String login) {
		this.login = login;
	}

	@Override
	public String toSqlQuery() {
		return String.format("WHERE login_u = '%s'", login);
	}
}

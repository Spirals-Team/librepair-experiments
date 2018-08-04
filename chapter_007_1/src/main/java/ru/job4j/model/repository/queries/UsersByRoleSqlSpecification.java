package ru.job4j.model.repository.queries;

import ru.job4j.model.pojo.Roles;

/**
 * @author Yury Matskevich
 */
public class UsersByRoleSqlSpecification implements SqlSpecification {
	private Roles role;

	public UsersByRoleSqlSpecification(Roles role) {
		this.role = role;
	}

	@Override
	public String toSqlQuery() {
		return String.format(
				"WHERE role_u = '%s'",
				role
		);
	}
}

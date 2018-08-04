package ru.job4j.model.repository.queries;

/**
 * @author Yury Matskevich
 */
public class UserByIdSqlSpecification implements SqlSpecification {
	private int id;

	public UserByIdSqlSpecification(int id) {
		this.id = id;
	}

	@Override
	public String toSqlQuery() {
		return String.format(
				"WHERE id_u = %d",
				id
		);
	}
}

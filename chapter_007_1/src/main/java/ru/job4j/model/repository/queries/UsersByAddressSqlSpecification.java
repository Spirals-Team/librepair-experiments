package ru.job4j.model.repository.queries;

/**
 * @author Yury Matskevich
 */
public class UsersByAddressSqlSpecification implements SqlSpecification {
	private String country;
	private String city;

	public UsersByAddressSqlSpecification(String country, String city) {
		this.country = country;
		this.city = city;
	}

	@Override
	public String toSqlQuery() {
		return String.format(
				"WHERE country_u = '%s' AND city_u = '%s'",
				country,
				city
		);
	}
}

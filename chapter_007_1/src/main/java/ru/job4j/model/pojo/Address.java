package ru.job4j.model.pojo;

import java.util.Objects;

/**
 * @author Yury Matskevich
 */
public class Address {
	private int id;
	private String country;
	private String city;

	public Address(String country, String city) {
		this.country = country;
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Address address = (Address) o;
		return id == address.id
				&& Objects.equals(country, address.country)
				&& Objects.equals(city, address.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, country, city);
	}
}

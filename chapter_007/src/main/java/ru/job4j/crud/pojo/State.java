package ru.job4j.crud.pojo;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public class State {
	private int id;
	private String name;
	private List<City> cities;

	public State(int id, String name, List<City> cities) {
		this.id = id;
		this.name = name;
		this.cities = cities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}

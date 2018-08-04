package ru.job4j.crud.pojo;

/**
 * @author Yury Matskevich
 */
public class City {
	private int id;
	private String name;

	public City(int id, String name) {
		this.id = id;
		this.name = name;
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
}

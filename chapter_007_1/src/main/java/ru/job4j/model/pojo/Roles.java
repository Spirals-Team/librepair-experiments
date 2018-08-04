package ru.job4j.model.pojo;

/**
 * @author Yury Matskevich
 */
public enum Roles {
	ADMIN(1), MODERATOR(2), USER(3);

	private int id;

	Roles(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

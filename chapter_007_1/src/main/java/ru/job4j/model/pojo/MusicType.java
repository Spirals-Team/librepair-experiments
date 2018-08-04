package ru.job4j.model.pojo;

/**
 * @author Yury Matskevich
 */
public enum MusicType {
	RAP(1), ROCK(2), FOLK(3), CLASSIC(4);

	private int id;

	MusicType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}

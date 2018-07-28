package ru.job4j.jdbc.xml.entity;

/**
 * @author Yury Matskevich
 */
public class Entry {
	private int field;

	public Entry() {

	}

	public Entry(int field) {
		this.field = field;
	}

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}
}

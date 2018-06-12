package ru.job4j.jdbc.tracker.dao;

import java.sql.Date;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class Item {
	private int id;
	private String name;
	private String description;
	private Date create;
	private List<String> comments;

	public Item(int id, String name, String description, Date create) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.create = create;
	}

	public Item(String name, String description, Date create) {
		this.name = name;
		this.description = description;
		this.create = create;
	}

	public Item() {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
}

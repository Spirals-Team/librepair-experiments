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
	private List<Comment> comments;

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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreate() {
		return create;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}

package ru.job4j.tracker.dao;

import java.util.Objects;

/**
 * @author Yury Matskevich
 */
public class Comment {
	private int id;
	private String comment;

	public Comment(String comment) {
		this.comment = comment;
	}

	public Comment(int id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Comment comment1 = (Comment) o;
		return id == comment1.id
				&& Objects.equals(comment, comment1.comment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, comment);
	}
}

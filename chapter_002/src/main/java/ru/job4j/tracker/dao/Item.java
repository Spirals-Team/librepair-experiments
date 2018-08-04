package ru.job4j.tracker.dao;

import java.util.List;
import java.util.Objects;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private long create;
    private List<Comment> comments;

	public Item(String name, String description, long create) {
		this.name = name;
		this.description = description;
		this.create = create;
		this.id = createId();
	}

	private String createId() {
    	return Integer.toString(
    			Objects.hash(name, description, create)
		);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public long getCreate() {
		return create;
	}

	public void setCreate(long create) {
		this.create = create;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Item item = (Item) o;
		return create == item.create
				&& Objects.equals(id, item.id)
				&& Objects.equals(name, item.name)
				&& Objects.equals(description, item.description)
				&& Objects.equals(comments, item.comments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, create, comments);
	}
}

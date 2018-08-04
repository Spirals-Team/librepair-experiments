package ru.job4j.tracker.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TrackerList implements ITracker {
    /**
     * Массив для хранение заявок.
     */
    private List<Item> items = new ArrayList<>();

    @Override
    public Item add(Item item) {
        this.items.add(item);
        return item;
    }

	@Override
    public void update(String id, Item item) {
        for (Item elem : this.items) {
            if (id.equals(elem.getId())) {
                elem.setName(item.getName());
                elem.setDescription(item.getDescription());
                elem.setCreate(item.getCreate());
                break;
            }
        }
    }

	@Override
    public void delete(String id) {
        int i = 0;
        for (Item elem : items) {
            if (elem.getId().equals(id)) {
                break;
            }
            i++;
        }
        items.remove(i);
    }

	@Override
    public List<Item> findAll() {
        return items;
    }

	@Override
    public List<Item> findByName(String key) {
		List<Item> cur = new ArrayList<>();
        for (Item elem : this.items) {
            if (elem.getName().equals(key)) {
                cur.add(elem);
            }
        }
        return cur;
    }

	@Override
    public Item findById(String id) {
        Item cur = null;
        for (Item elem : this.items) {
            if (elem.getId().equals(id)) {
                cur = elem;
            }
        }
        return cur;
    }

	@Override
	public void writeComment(String id, String comment) {
		Item cur = findById(id);
		List<Comment> comments = cur.getComments();
		if (comments == null) {
			cur.setComments(new ArrayList<>());
		}
		cur.getComments().add(new Comment(comment));
	}
}

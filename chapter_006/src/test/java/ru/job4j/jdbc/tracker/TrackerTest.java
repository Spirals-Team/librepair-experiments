package ru.job4j.jdbc.tracker;

import org.junit.Test;
import ru.job4j.jdbc.tracker.connect.PostgreDb;
import ru.job4j.jdbc.tracker.dao.Item;
import ru.job4j.jdbc.tracker.dao.ItemDao;

import java.sql.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 */
public class TrackerTest {
	private String pathProp = getClass()
			.getClassLoader()
			.getResource("config.properties")
			.getPath();
	private String pathCreateDb = getClass()
			.getClassLoader()
			.getResource("createDb.sql")
			.getPath();
	private String pathCreateStr = getClass()
			.getClassLoader()
			.getResource("createStructur.sql")
			.getPath();

	@Test
	public void t() {
		Tracker tracker = new Tracker(new ItemDao(new PostgreDb(pathProp, pathCreateDb, pathCreateStr)));
		Item item = new Item("first", "fisF", new Date(System.currentTimeMillis()));
		tracker.add(item);
		String result = tracker.findAll().get(0).getName();
		assertEquals("first", result);
	}
}

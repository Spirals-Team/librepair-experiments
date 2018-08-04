package ru.job4j.jdbc.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.jdbc.tracker.connect.PostgreDb;
import ru.job4j.jdbc.tracker.dao.Comment;
import ru.job4j.jdbc.tracker.dao.Item;
import ru.job4j.jdbc.tracker.dao.ItemDao;
import ru.job4j.jdbc.tracker.load.LoadResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Yury Matskevich
 */
public class TrackerTest {
	//A name of configuration file
	private String conf = "/config.properties";
	private Tracker tracker;

	//setting up db dates for testing
	@Before
	public void setUp() {
		tracker = new Tracker(new ItemDao(new PostgreDb(conf)));
		Item item1 = new Item(
				"first",
				"firstComment",
				new Date(System.currentTimeMillis())
		);
		Item item2 = new Item(
				"second",
				"secondComment",
				new Date(System.currentTimeMillis())
		);
		Item item3 = new Item(
				"first",
				"thirdComment",
				new Date(System.currentTimeMillis())
		);
		tracker.add(item1);
		tracker.add(item2);
		tracker.add(item3);
	}

	//for deleting database after each test
	@After
	public void deleteDb() throws SQLException {
		LoadResource res = new LoadResource(conf);
		try (Connection conn = DriverManager.getConnection(
				String.format(
						"%s%s", res.getProperty("db.path"), "postgres"
				),
				res.getProperty("db.username"),
				res.getProperty("db.password"));
			 Statement st = conn.createStatement()) {
			st.execute(
					String.format(
							"DROP DATABASE %s", res.getProperty("db.name")
					)
			);
		}
	}

	@Test
	public void testAddAnbFindAll() {
		String result = tracker.findAll().get(0).getName();
		assertEquals("first", result);
	}

	@Test
	public void testUpdate() {
		Item itemUpdate = new Item(
				"update first",
				"update firstComment",
				new Date(System.currentTimeMillis())
		);
		tracker.update(1, itemUpdate);
		List<Item> result = tracker.findAll();
		assertEquals("update first", result.get(0).getName());
		assertEquals("update firstComment", result.get(0).getDescription());
	}

	@Test
	public void testDelete() {
		tracker.delete(1);
		tracker.delete(2);
		tracker.delete(3);
		assertNull(tracker.findAll());
	}

	@Test
	public void testFindByName() {
		String name = "first";
		List<Item> result = tracker.findByName(name);
		for (Item item : result) {
			assertEquals(name, item.getName());
		}
	}

	@Test
	public void testFindById() {
		int id = 1;
		Item result = tracker.findById(id);
		assertEquals("first", result.getName());
		assertEquals("firstComment", result.getDescription());
	}

	@Test
	public void testWritingComment() {
		int id = 1; //an id of item for testing
		String comment1 = "this is first comment";
		String comment2 = "this is second comment";
		tracker.writeComment(id, comment1);
		tracker.writeComment(id, comment2);
		List<Comment> expectedComments =
				new ArrayList<>(
						Arrays.asList(
								new Comment(1, comment1),
								new Comment(2, comment2)
						)
				);
		List<Comment> actualComments = tracker.findById(id).getComments();
		assertEquals(expectedComments, actualComments);
	}
}

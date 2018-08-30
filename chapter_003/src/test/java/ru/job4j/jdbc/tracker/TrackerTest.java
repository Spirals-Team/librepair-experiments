package ru.job4j.jdbc.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class TrackerTest {

    public static final String DO_SMTH = "do smth";
    private SQLStorage storage;
    private Tracker tracker;

    @Before
    public void setUp() {
        storage = new SQLStorage();
        storage.initDB();
        tracker = new Tracker();
    }

    @After
    public void setDown() {
        storage.dropTable();
    }

    @Test
    public void addItemToDB() {
        String name = String.valueOf(System.currentTimeMillis());
        Item item = tracker.add(new Item(name, DO_SMTH));
        assertThat(item.getName(), is(name));
    }

    @Test
    public void deleteItemFromDB() {
        String name = String.valueOf(System.currentTimeMillis());
        Item item = tracker.add(new Item(name, DO_SMTH));
        assertThat(item.getName(), is(name));
        tracker.delete(item.getId());
        Item deletedItem = tracker.findById(item.getId());
        assertThat(deletedItem.getName(), isEmptyOrNullString());
    }

    @Test
    public void selectAllFromDB() {
        String name = String.valueOf(System.currentTimeMillis());
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item(name, DO_SMTH));
        List<Item> items = tracker.findAll();
        assertThat(items, hasSize(5));
    }

    @Test
    public void findItemsByNameInDB() {
        String name = String.valueOf(System.currentTimeMillis());
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item(name, DO_SMTH));
        tracker.add(new Item("not found", DO_SMTH));
        List<Item> items = tracker.findByName(name);
        assertThat(items, hasSize(2));
    }

    @Test
    public void replaceItemInDB() {
        String name1 = "name1-" + System.currentTimeMillis();
        String name2 = "name2-" + System.currentTimeMillis();
        Item oldItem = tracker.add(new Item(name1, DO_SMTH));
        Item itemToReplace = tracker.add(new Item(name2, DO_SMTH));
        tracker.replace(oldItem.getId(), itemToReplace);
        Item newItem = tracker.findById(oldItem.getId());
        assertThat(newItem.getName(), is(itemToReplace.getName()));
    }
}
package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */
public class TrackerTest {
    private Tracker tracker = new Tracker();

    @Test
    public void add() {
        Item item = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        tracker.add(item);
        tracker.add(item2);
        assertThat(tracker.findAll().get(0), is(item));
        assertThat(tracker.findAll().get(1), is(item2));
    }

    @Test
    public void replace() {
        Item item = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        tracker.add(item);
        tracker.replace(item.getId(), item2);
        assertThat(tracker.findById(item.getId()).getName(), is("second item"));
    }

    @Test
    public void delete() {
        Item item = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        Item item3 = new Item("third", "third description", 789L);
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        tracker.delete(item2.getId());
        assertThat(tracker.findAll().get(0), is(item));
        assertThat(tracker.findAll().get(1), is(item3));
    }

    @Test
    public void findAll() {
        Item item = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        Item item3 = new Item("third", "third description", 789L);
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> findAll = tracker.findAll();
        List<Item> expected = new ArrayList<>(Arrays.asList(item, item2, item3));
        assertThat(findAll, is(expected));
    }

    @Test
    public void findById() {
        Item item = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        Item item3 = new Item("third", "third description", 789L);
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        Item expected = tracker.findById(item2.getId());
        assertThat(expected, is(item2));
    }

    @Test
    public void findByName() {
        Item item = new Item("third", "first description", 123L);
        Item item2 = new Item("second item", "second description", 456L);
        Item item3 = new Item("third", "third description", 789L);
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> result = tracker.findByName(item3.getName());
        List<Item> expected = new ArrayList<>(Arrays.asList(item, item3));
        assertThat(expected, is(result));
    }
}
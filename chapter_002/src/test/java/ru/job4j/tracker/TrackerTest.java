package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        List<Item> expect = new ArrayList<>();
        expect.add(item);
        assertThat(tracker.getAll(), is(expect));
    }
    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        next.setId(previous.getId());
        tracker.replace(next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }
    @Test
    public  void whenFindidThenReturnId() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testdesc", 123L);
        tracker.add(item);
        assertThat((tracker.findById(item.getId()).getName()), is("test1"));
    }
    @Test
    public void whenDeleteItemThenReturnNewArrayWithoutItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "desc1", 123L);
        tracker.add(item);
        Item next = new Item("test2", "testDescription2", 1234L);
        tracker.add(next);
        Item next2 = new Item("test22", "testDescription22", 12342L);
        tracker.add(next2);
        tracker.delete(next.getId());
        List<Item> expectList = Arrays.asList(item, next2);
        assertThat(tracker.getAll(), is(expectList));
    }
    @Test
    public void whenFindByNameWhenReturnByName() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "desc1", 123L);
        tracker.add(item);
        Item next = new Item("test1", "desc41", 1243L);
        tracker.add(next);
        Item previuos = new Item("test12", "desc1", 123L);
        tracker.add(previuos);
        List<Item> expectList = Arrays.asList(item, next);
        assertThat(tracker.findByName(item.getName()), is(expectList));
    }
    @Test
    public  void whenFindAllWhenReturnAllItemsWithoutNull() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "desc1", 123L);
        tracker.add(item);
        Item next = new Item("test1", "desc1", 123L);
        tracker.add(next);
        Item third = new Item("test1", "desc1", 123L);
        tracker.add(third);
        List<Item> expectList = Arrays.asList(item, next, third);
        assertThat(tracker.getAll().toString(), is(expectList.toString()));
    }
}

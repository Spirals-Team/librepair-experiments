package ru.job4j.tracker;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * TrackerTest.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class TrackerTest {
    /*** Add item and find all.*/
    @Test
    public void whenAddItemThenTrackerHasThatItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }
    /*** Delete item.*/
    @Test
    public void whenDeleteItemThenTrackerHasNotThatItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        tracker.delete(item);
        Tracker expectTracker = new Tracker();
        assertThat(tracker.findAll(), is(expectTracker.findAll()));
    }
    /*** Find by id and update item.*/
    @Test
    public void whenUpdateNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        tracker.update(next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }
    /*** Find items by name.*/
    @Test
    public void whenFindItemByNameThenReturnItemGivenName() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findByName("test").get(0).getName(), is("test"));
    }
}

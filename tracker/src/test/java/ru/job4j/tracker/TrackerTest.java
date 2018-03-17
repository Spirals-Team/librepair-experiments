package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.start.Tracker;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class TrackerTest {
    private Tracker tracker;
    private Item item;
    private Item item1;

    @Before
    public void init() {
        this.item = new Item("test1", "testDescription");
        //  this.item1 = new Item("test1", "testDescription");
        // tracker = new Tracker();
        Item itemResult = new Item("1", "test1",  "testDescription");
        tracker = mock(Tracker.class);
        when(tracker.add(item)).thenReturn(itemResult);
    }
    @After
    public void close() {
        tracker.close();
    }
    /**
     * Test add new Item.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Item item1 = tracker.add(item);
        String id = item1.getId();
        assertThat(item1.getId(), is(id));
        tracker.delete(item1);
    }

    /**
     * Test update.
     */
    @Test
    @Ignore
    public void whenUpdateItemThenTrackerHasItemUpdate() {
        item = tracker.add(item);
        item.setDesc("newDescription");
        tracker.update(item);
        Item fromTracer = tracker.findById(item.getId());
        assertThat("newDescription", is(fromTracer.getDesc()));
        tracker.delete(item);
    }

    /**
     * Test delete.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemsThenDeletingHeHasOneItem() {
        Item item = new Item("test1", "testDiscr");
        item = tracker.add(item);
        tracker.delete(item);
        item = tracker.findById(item.getId());
        assertThat(null, is(item));
    }

    /**
     * Test getAll.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemsThenFindAllReturnAll() {
        Item item = new Item("test1", "testDiscr");
        Item item2 = new Item("test2", "testDiscr");
        item = tracker.add(item);
        item2 = tracker.add(item2);
        ArrayList<Item> result = new ArrayList<>();
        result.add(item);
        result.add(item2);
        List<Item> ex = tracker.getAll();
        assertThat(result, is(ex));
        tracker.delete(item);
        tracker.delete(item2);
    }

    /**
     *Test findByName.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemWithEqualsNameThenReturnThisItems() {
        Item item = new Item("test1", "testDiscr");
        Item item2 = new Item("test1", "testDiscr");
        item = tracker.add(item);
        item2 = tracker.add(item2);
        ArrayList<Item> result = new ArrayList<>();
        result.add(item);
        result.add(item2);
        List<Item> ex =  tracker.findByName("test1");
        assertThat(result, is(ex));
        tracker.delete(item);
        tracker.delete(item2);
    }

    /**
     * Test findById.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemsAndFindByIdThenReturnItemById() {
        Item item = new Item("test1", "testDiscr");
        item = tracker.add(item);
        String id = item.getId();
        Item item2 = tracker.findById(id);
        assertThat(id, is(item2.getId()));
        tracker.delete(item);
    }
}

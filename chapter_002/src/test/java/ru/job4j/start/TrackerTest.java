package ru.job4j.start;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Sergey Afonin (mailto afonin1c@mail.ru)
 * @since 19.10.2017
 * sql added 26.01.2018
 */
public class TrackerTest {

    /**
     * test add, edit methods and test inheritance/incapsulation.
     */
    @Ignore
    @Test
    public void whenUpdateNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Item previous = new Task("test1", "testDescription", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Task("test2", "testDescription2", 1234L);
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.edit(next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     * test add method.
     */
    @Ignore
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.getAll()[0], is(item));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     * test update method.
     */
    @Ignore
    @Test
    public void whenUpdateItemThenReturnNewItem() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        item.setDescription("testDescription2");
        tracker.update(item);
        assertThat(tracker.findById(item.getId()).getDescription(), is("testDescription2"));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     * test delete method.
     */
    @Ignore
    @Test
    public void whenDeleteItemThenReturnNull() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item item2 = new Item("test2", "testDescription2", 333L);
        tracker.add(item2);

        tracker.delete(item);

        Item impliedValue = null;
        assertThat(tracker.findById(item.getId()), is(impliedValue));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     * test findAll method.
     */
    @Ignore
    @Test
    public void  whenFindAllItemsThenReturnNotNullItems() {
        Tracker tracker = new Tracker();
        tracker.initTracker();

        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item item2 = new Item("test2", "testDescription2", 222L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription3", 333L);
        tracker.add(item3);
        Item item4 = new Item("test4", "testDescription4", 444L);
        tracker.add(item4);

        tracker.delete(item2);
        tracker.delete(item3);

        Item[] impliedArrayOfItems = new Item[2];
        impliedArrayOfItems[0] = item;
        impliedArrayOfItems[1] = item4;

        assertThat(tracker.findAll(), is(impliedArrayOfItems));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }
    /**
     * test findByName method.
     */
    @Ignore
    @Test
    public void whenFindByNameThenReturnOnlyWtihTheSameNameItems() {
        Tracker tracker = new Tracker();
        tracker.initTracker();

        Item item = new Item("keyName", "testDescription", 123L);
        tracker.add(item);
        Item item2 = new Item("test2", "testDescription2", 222L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription3", 333L);
        tracker.add(item3);
        Item item4 = new Item("keyName", "testDescription4", 444L);
        tracker.add(item4);

        ArrayList<Item> impliedArrayOfItems = new ArrayList<Item>();
        impliedArrayOfItems.add(item);
        impliedArrayOfItems.add(item4);

        assertThat(tracker.findByName("keyName"), is(impliedArrayOfItems));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     * test findById method.
     */
    @Ignore
    @Test
    public void whenFinkByIdThenReturnOnlyOneElementWithTheSameId() {

        Tracker tracker = new Tracker();
        tracker.initTracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        Item expecteItem = new Item("test2", "testDescription2", 333L);
        tracker.add(expecteItem);

        String idExpectedItem = expecteItem.getId();

        assertThat(tracker.findById(idExpectedItem), is(expecteItem));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }
}
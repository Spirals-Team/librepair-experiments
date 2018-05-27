package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription");
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription");
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2");
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenGenerateNewId() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        String idOne = item.getId();
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        String idTwo = item.getId();
        boolean rsl = idOne.equals(idTwo) ? true : false;
        boolean expect = false;
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteSecondItemInList() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        String key = item.getId();
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        tracker.delete(key);
        List<String> rsl = new ArrayList<>();
        for (Item it : tracker.findAll()) {
            rsl.add(it.getName());
        }
        List<String> expect = new ArrayList<>();
        expect.add("test1");
        expect.add("test3");
        expect.add("test4");
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteFirstItemInList() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        String key = item.getId();
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        tracker.delete(key);
        List<String> rsl = new ArrayList<String>();
        for (Item it : tracker.findAll()) {
            rsl.add(it.getName());
        }
        List<String> expect = new ArrayList<String>();
        expect.add("test2");
        expect.add("test3");
        expect.add("test4");
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteLastItemInList() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        String key = item.getId();
        tracker.delete(key);
        List<String> rsl = new ArrayList<String>();
        for (Item it : tracker.findAll()) {
            rsl.add(it.getName());
        }
        List<String> expect = new ArrayList<String>();
        expect.add("test1");
        expect.add("test2");
        expect.add("test3");
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenFindAll() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        List<String> rsl = new ArrayList<String>();
        for (Item it : tracker.findAll()) {
            rsl.add(it.getName());
        }
        List<String> expect = new ArrayList<String>();
        expect.add("test1");
        expect.add("test2");
        expect.add("test3");
        expect.add("test4");
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription1");
        tracker.add(item);
        item = new Item("test", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        item = new Item("test", "testDescription5");
        tracker.add(item);
        List<String> rsl = new ArrayList<String>();
        for (Item it : tracker.findByName("test")) {
            rsl.add(it.getName());
        }
        List<String> expect = new ArrayList<String>();
        expect.add("test");
        expect.add("test");
        expect.add("test");
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenFindById() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        Item expect = item;
        String key = item.getId();
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        Item rsl = tracker.findById(key);
        assertThat(rsl, is(expect));
    }
}
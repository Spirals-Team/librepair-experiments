package ru.job4j.profession.tracker;

import org.junit.Test;
import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.models.Task;
import ru.job4j.tracker.start.Tracker;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class TrackerTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 29.09.2017
 */
public class TrackerTest {
    /**
     * Метод whenUpdateNameThenReturnNewName. Создаем новую задачу а потом обновляем её.
     */
    @Test
    public void whenUpdateNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Task("test1", "testDescription", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Task("test2", "testDescription2", 1234L);
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.update(next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    /**
     * Метод whenAddNewItemThenTrackerHasSameItem. Создаем новую задачу и проверяем имеет ли трекер её.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.getAll().get(0), is(item));
    }

    /**
     * Метод deleteItemTracker. Создаем новую задачу, потом удаляем его и проверяем удалился ли он.
     */
    @Test
    public void deleteItemTracker() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        tracker.delete(item);
        assertThat(tracker.getAll().isEmpty(), is(true));
    }

    /**
     * Метод getAListOfAllTask. Создаем задачи и проверяем вернет ли getAll все задачи.
     */
    @Test
    public void getAListOfAllTask() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1", 123L));
        tracker.add(new Item("test2", "testDescription2", 1234L));
        tracker.add(new Item("test3", "testDescription3", 12345L));
        assertThat(tracker.getAll().size(), is(3));
    }

    /**
     * Метод getListOfName. Создаем задачи и проверяем вернет ли метод findByName задачи с одинаковыми именами.
     */
    @Test
    public void getListOfName() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1", 123L));
        tracker.add(new Item("test1", "testDescription1", 123L));
        tracker.add(new Item("test3", "testDescription3", 12345L));
        assertThat(tracker.findByName("test1").size(), is(2));
    }

    /**
     * Метод getItemOfId проверяет найдет ли метод findById задачу по id.
     */
    @Test
    public void getItemOfId() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1", 123L));
        ArrayList<Item> item = tracker.findByName("test1");
        String source = tracker.findById(item.get(0).getId()).getId();
        assertThat(source, is(item.get(0).getId()));
    }

    /**
     * Метод findAllItem проверяет вернет ли метод findAll список задач без null элементов.
     */
    @Test
    public void findAllItem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1", 123L));
        tracker.add(new Item("test1", "testDescription1", 123L));
        assertThat(tracker.findAll().size(), is(2));
    }
}

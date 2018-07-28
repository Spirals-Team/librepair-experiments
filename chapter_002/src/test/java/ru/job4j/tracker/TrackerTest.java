package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;
import ru.job4j.tracker.connect.PostgreDb;
import ru.job4j.tracker.dao.*;
import ru.job4j.tracker.load.LoadResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public abstract class TrackerTest {
    /**
     * Метод тестирующий добовление заявки
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
		ITracker tracker = getTracker();
        Item item = new Item("test1", "testDescription1", 123L);
        tracker.add(item);
        assertThat(tracker.add(item), is(tracker.findAll().get(0)));
    }

    /**
     * Метод тестирующий поиск заявки по id, при условии что заяка есть в Tracker
     */
    @Test
    public void whenThereIsFormInTrackerAndWeFindByItsIdThenGetTheForm() {
		ITracker tracker = getTracker();
        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 123L);
        Item third = new Item("test3", "testDescription3", 123L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        assertThat(tracker.findById(second.getId()), is(second));
    }

    /**
     * Метод тестирующий поиск заявки по id, при условии что заяки нет в Tracker
     */
    @Test
    public void whenThereIsNoFormInTrackerAndWeFindByItsIdThenGetNull() {
		ITracker tracker = getTracker();
        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 123L);
        tracker.add(first);
        assertNull(tracker.findById(second.getId()));
    }

    /**
     * Метод тестирующий поиск заявки по name, , при условии что заяка есть в Tracker
     */
    @Test
    public void whenThereIsFormInTrackerAndWeFindByNameThenGetAllTheFormWithThatName() {
		ITracker tracker = getTracker();
        Item first1 = new Item("test1", "testDescription1", 1231L);
        Item first2 = new Item("test1", "testDescription2", 1232L);
        Item third = new Item("test3", "testDescription3", 123L);
        Item first3 = new Item("test1", "testDescription3", 1233L);
        tracker.add(first1);
        tracker.add(first2);
        tracker.add(third);
        tracker.add(first3);
        List<Item> arrayItem1 = new ArrayList<>(Arrays.asList(first1, first2, first3));
        List<Item> arrayItem2 = tracker.findByName(first1.getName());
        for (int i = 0; i < 3; i++) {
            assertThat(arrayItem1.get(i).getName(), is(arrayItem2.get(i).getName()));
        }
    }

    /**
     * Метод тестирующий обновление заявки с заданным id
     */
    @Test
    public void whenReplaceNameThenReturnNewName() {
		ITracker tracker = getTracker();
        Item previous = new Item("test1", "testDescription1", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2", 1234L);
        // Обновляем заявку в трекере.
        tracker.update(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые значения полей.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
        assertThat(tracker.findById(previous.getId()).getDescription(), is("testDescription2"));
        assertThat(tracker.findById(previous.getId()).getCreate(), is(1234L));
    }

    /**
     * Метод тестирующий возврат всех заявок, которые есть в Tacker
     */
    @Test
    public void whenTrackerHasSameItemsThenWeGetAllTheseItems() {
		ITracker tracker = getTracker();
        Item first = new Item("test1", "testDescription1", 1231L);
        Item second = new Item("test2", "testDescription2", 1232L);
        Item third = new Item("test3", "testDescription3", 1233L);
        Item fourth = new Item("test4", "testDescription4", 1234L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);
        Set<Item> expectedSet = new HashSet<>(Arrays.asList(first, second, third, fourth));
        Set<Item> actualSet = new HashSet<>(tracker.findAll());
		assertEquals(expectedSet, actualSet);
    }

    /**
     * Метод тестирующий удаление заявки из Tracker
     */
    @Test
    public void whenDeleteFormThenArrayDoNotHasThatForm() {
		ITracker tracker = getTracker();
        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 1234L);
        Item third = new Item("test3", "testDescription3", 1233L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.delete(second.getId());
        Item[] arrayItem = {first, third};
        for (int i = 0; i < 2; i++) {
            assertThat(tracker.findAll().get(i), is(arrayItem[i]));
        }
    }

	@Test
	public void testWritingComment() {
		ITracker tracker = getTracker();
		Item first = new Item("test1", "testDescription1", 123L);
		tracker.add(first);
		String id = first.getId(); //an id of item for testing
		String comment1 = "this is first comment";
		String comment2 = "this is second comment";
		tracker.writeComment(id, comment1);
		tracker.writeComment(id, comment2);
		List<String> expectedComments = Arrays.asList(comment1, comment2);
		List<String> actualComments = new ArrayList<>();
		for (Comment item : tracker.findById(id).getComments()) {
			actualComments.add(item.getComment());
		}
		assertEquals(expectedComments, actualComments);
	}

	protected abstract ITracker getTracker();
}

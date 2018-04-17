package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TrackerTest {

    /**
     * Метод тестирующий получение Id заявки
     */
    @Test
    public void whenIndexOfThenGetId() {
        Tracker tracker = new Tracker();

        Item item = new Item("test1", "testDescription1", 123L);

        assertThat(tracker.indexOf(item), is(item.getId()));
    }

    /**
     * Метод тестирующий добовление заявки
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();

        Item item = new Item("test1", "testDescription1", 123L);

        tracker.add(item);

        assertThat(tracker.add(item), is(item));
    }

    /**
     * Метод тестирующий поиск заявки по id, при условии что заяка есть в Tracker
     */
    @Test
    public void whenThereIsFormInTrackerAndWeFindByItsIdThenGetTheForm() {
        Tracker tracker = new Tracker();

        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 123L);
        Item third = new Item("test3", "testDescription3", 123L);

        tracker.add(first);
        tracker.add(second);
        tracker.add(third);

        assertThat(tracker.findById(tracker.indexOf(second)), is(second));
    }

    /**
     * Метод тестирующий поиск заявки по id, при условии что заяки нет в Tracker
     */
    @Test
    public void whenThereIsNoFormInTrackerAndWeFindByItsIdThenGetNull() {
        Tracker tracker = new Tracker();

        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 123L);

        tracker.add(first);

        Item[] cur = null;

        assertThat(tracker.findById(tracker.indexOf(second)), is(cur));
    }

    /**
     * Метод тестирующий поиск заявки по name, , при условии что заяка есть в Tracker
     */
    @Test
    public void whenThereIsFormInTrackerAndWeFindByNameThenGetAllTheFormWithThatName() {
        Tracker tracker = new Tracker();

        Item first1 = new Item("test1", "testDescription1", 1231L);
        Item first2 = new Item("test1", "testDescription2", 1232L);
        Item third = new Item("test3", "testDescription3", 123L);
        Item first3 = new Item("test1", "testDescription3", 1233L);

        tracker.add(first1);
        tracker.add(first2);
        tracker.add(third);
        tracker.add(first3);

        Item[] arrayItem1 = {first1, first2, first3};
        Item[] arrayItem2 = tracker.findByName(first1.getName());

        for (int i = 0; i < 3; i++) {
            assertThat(arrayItem1[i].getName(), is(arrayItem2[i].getName()));
        }
    }

    /**
     * Метод тестирующий обновление заявки с заданным id
     */
    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription1", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2", 1234L);
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые значения полей.
        assertThat(tracker.findById(tracker.indexOf(previous)).getName(), is("test2"));
        assertThat(tracker.findById(tracker.indexOf(previous)).getDescription(), is("testDescription2"));
        assertThat(tracker.findById(tracker.indexOf(previous)).getCreate(), is(1234L));
    }

    /**
     * Метод тестирующий возврат всех заявок, которые есть в Tacker
     */
    @Test
    public void whenTrackerHasSameItemsThenWeGetAllTheseItems() {
        Tracker tracker = new Tracker();

        Item first = new Item("test1", "testDescription1", 1231L);
        Item second = new Item("test2", "testDescription2", 1232L);
        Item third = new Item("test3", "testDescription3", 1233L);
        Item fourth = new Item("test4", "testDescription4", 1234L);

        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.add(fourth);

        Item[] arrayItem = {first, second, third, fourth};
        for (int i = 0; i < 4; i++) {
            assertThat(arrayItem[i], is(tracker.findAll()[i]));
        }
    }

    /**
     * Метод тестирующий удаление заявки из Tracker
     */
    @Test
    public void whenDeleteFormThenArrayDoNotHasThatForm() {
        Tracker tracker = new Tracker();

        Item first = new Item("test1", "testDescription1", 123L);
        Item second = new Item("test2", "testDescription2", 1234L);
        Item third = new Item("test3", "testDescription3", 1233L);

        tracker.add(first);
        tracker.add(second);
        tracker.add(third);

        tracker.delete(second.getId());

        Item[] arrayItem = {first, third};

        for (int i = 0; i < 2; i++) {
            assertThat(tracker.findAll()[i], is(arrayItem[i]));
        }
    }
}

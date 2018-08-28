package ru.job4j.profession.tracker;

import org.junit.Test;
import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.start.Input;
import ru.job4j.tracker.start.StartUI;
import ru.job4j.tracker.start.StubInput;
import ru.job4j.tracker.start.Tracker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test сlass StubInputTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 08.10.2017
 */
public class StubInputTest {
    /**
     * Тестируем добавление задачи.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "y"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.getAll().get(0).getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    /**
     * Проверяем вернутс ли все задачи.
     */
    @Test
    public void showAllItem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1", 123L));
        tracker.add(new Item("test2", "testDescription2", 1234L));
        tracker.add(new Item("test3", "testDescription3", 12345L));
        Input input = new StubInput(new String[]{"1", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.getAll().size(), is(3));
    }

    /**
     * Проверяем обновится ли задача.
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("Test", "TestTest"));
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "y"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    /**
     * Проверяем удалится ли задача.
     */
    @Test
    public void deleteItem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1"));
        Input input = new StubInput(new String[]{"3", tracker.getAll().get(0).getId(), "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.getAll().isEmpty(), is(true));
    }

    /**
     * Проверяем будет ли поиск по id.
     */
    @Test
    public void getItemOfId() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1"));
        Input input = new StubInput(new String[]{"4", tracker.getAll().get(0).getId(), "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(tracker.getAll().get(0).getId()).getId(), is(tracker.findAll().get(0).getId()));
    }

    /**
     * Проверяем будет ли поиск по name.
     */
    @Test
    public void findItemByName() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("test1", "testDescription1"));
        Input input = new StubInput(new String[]{"5", "test1", "y"});
        new StartUI(input, tracker).init();

        assertThat(tracker.findByName("test1").get(0).getName(), is("test1"));
    }
}

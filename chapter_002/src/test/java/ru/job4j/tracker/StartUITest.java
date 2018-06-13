package ru.job4j.tracker;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */


public class StartUITest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String menu = "0: Добавление новой заявки\r\n"
            + "1: Показать все заявки\r\n"
            + "2: Редактирование заявки\r\n"
            + "3: Удаление заявки\r\n"
            + "4: Поиск заявки по ID\r\n"
            + "5: Поиск заявки по имени\r\n"
            + "6: Выход из программы";

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findAll().get(0).getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

//    @Test
//    public void whenShowAll() {
//        Tracker tracker = new Tracker();
//        Item item = tracker.add(new Item("test name", "decs,", 123L));
//        List<Item> items = tracker.findAll();
//        String expected = new StringBuilder()
//                .append(this.menu).append(System.lineSeparator()).
//                        append("Список всех заявок:" + items.size()).append(System.lineSeparator()).
//                        append("Имя: ").append("test name").append(" ID: ").append(item.getId()).append(System.lineSeparator()).
//                        append(this.menu).append(System.lineSeparator()).toString();
//        Input input = new StubInput(new String[]{"1", "6"});
//        StartUI start = new StartUI(input, tracker);
//        this.loadOutput();
//        start.init();
//        assertThat(new String(this.out.toByteArray()), is(expected));
//        this.backOutput();
//    }

    @Test
    public void whenEditThenTrackerHasEditValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "decs", 123L));
        Input input = new StubInput(new String[]{"2", item.getId(), "edit name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("edit name"));
    }

    @Test
    public void whenUserDeleteItem() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("first item", "first description", 123L);
        Item item2 = new Item("second item", "second description", 132L);
        Item item3 = new Item("third item", "third description", 123L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        Input input = new StubInput(new String[]{"3", tracker.findAll().get(1).getId(), "6"});
        new StartUI(input, tracker).init();
        List<Item> items = tracker.findAll();
        assertThat(items.get(0).getName(), is("first item"));
        assertThat(items.get(1).getName(), is("third item"));
    }

    @Test
    public void whenFindItemThenItemByID() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "decs", 123L));
        Input input = new StubInput(new String[]{"4", item.getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenFindItemNameThenItemByName() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "decs", 123L));
        Input input = new StubInput(new String[]{"5", item.getName(), "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("name"));
    }
}
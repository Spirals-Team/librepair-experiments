package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StartUiTest {

    Tracker tracker = new Tracker();
    Item[] items = new Item[4];

    @Before
    public void beforeInit(){
        // создаем тестовые заявки
        items[0] = new Item("testname1", "testDesc1");
        items[1] = new Item("testname2", "testDesc2");
        items[2] = new Item("testname3", "testDesc3");
        items[3] = new Item("testname4", "testDesc5");
        // добавляем заявки в трекер
        tracker.add(items[0]);
        tracker.add(items[1]);
        tracker.add(items[2]);
        tracker.add(items[3]);
    }


    // Тест пункта меню Создание заявки
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        //   создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что пятый элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findAll().get(4).getName(), is("test name"));
    }

    // Тест пункта меню Редактирование заявки
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"2", items[2].getId(), "test name", "desc", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что третий элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(items[2].getId()).getName(), is("test name"));
    }

    // Тест пункта меню Удаление заявки
    @Test
    public void whenDelItemThenTrackerDoesIt() {
        // удаляем 3й элемент
        Input input = new StubInput(new String[]{"3", items[2].getId(), "6"});
        new StartUI(input, tracker).init();
        // проверяем осталась ли удаленная заявка а списке заявок
        assertThat(tracker.findById(items[2].getId()), is(nullValue()));
    }

    // Тест пункта меню Найти по имени
    @Test
    public void whenFindByNameThenTrackerHasTrue() {
        Input input = new StubInput(new String[]{"5",items[1].getName(), "6"});
        // Визуально видно что найден второй элемент
        new StartUI(input, tracker).init();
        // проверяем что элемент найденный по имени второй заявки является вторым элементом
        assertThat(tracker.findByName(items[1].getName()).get(0), is(items[1]));
    }

    // Тест пункта меню Найти по Id
    @Test
    public void whenFindByIdThenTrackerHasTrue() {
        Input input = new StubInput(new String[]{"4",items[2].getId(), "6"});
        // Визуально видно что найден третий элемент
        new StartUI(input, tracker).init();
        // проверяем что элемент найденный по id третей заявки является третьим элементом
        assertThat(tracker.findById(items[2].getId()), is(items[2]));
    }

    // Тест пункта меню Удаление заявки
/*    @Test
    public void whenDelItemThenTrackerShowThis() {
        // получаем ссылку на стандартный вывод в консоль.
        PrintStream stdout = System.out;
        // Создаем буфер для хранения вывода.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //Заменяем стандартный вывод на вывод в пямять для тестирования.
        System.setOut(new PrintStream(out));
        // удаляем 3й элемент
        Input input = new StubInput(new String[]{"3", items[2].getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("0. Добавить заявку\r\n")
                                .append("1. Посмотреть существующие заявки\r\n")
                                .append("2. Редактировать заявку\r\n")
                                .append("3. Удаление заявки\r\n")
                                .append("4. Поиск заявки по Id\r\n")
                                .append("5. Поиск заявки по имени\r\n")
                                .append("6. Выход из меню\r\n")
                                .append("------------ Удаление заявки --------------\r\n")
                                .append("Заявка удалена!!!\r\n")
                                .append("---------------------------------------------------------\r\n")
                                .append("0. Добавить заявку\r\n")
                                .append("1. Посмотреть существующие заявки\r\n")
                                .append("2. Редактировать заявку\r\n")
                                .append("3. Удаление заявки\r\n")
                                .append("4. Поиск заявки по Id\r\n")
                                .append("5. Поиск заявки по имени\r\n")
                                .append("6. Выход из меню\r\n")
                                .append("------------ Выход из меню --------------\r\n")
                                .toString()
                )
        );
        // возвращаем обратно стандартный вывод в консоль.
        System.setOut(stdout);

    }

    // Тест пункта меню Показать все заявки
    @Test
    public void whenShowItemsThenTrackerShowThis() {
        // получаем ссылку на стандартный вывод в консоль.
        PrintStream stdout = System.out;
        // Создаем буфер для хранения вывода.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //Заменяем стандартный вывод на вывод в пямять для тестирования.
        System.setOut(new PrintStream(out));
        // удаляем 3й элемент
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        ArrayList<Item> items=tracker.findAll();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("0. Добавить заявку\r\n")
                                .append("1. Посмотреть существующие заявки\r\n")
                                .append("2. Редактировать заявку\r\n")
                                .append("3. Удаление заявки\r\n")
                                .append("4. Поиск заявки по Id\r\n")
                                .append("5. Поиск заявки по имени\r\n")
                                .append("6. Выход из меню\r\n")
                                .append("------------ Все зарегистрированные заявки --------------\r\n")
                                .append("Имя заявки:").append(items.get(0).getName()).append(" Описание заявки:")
                                .append(items.get(0).getDesc()).append(" Код заявки:").append(items.get(0).getId()).append("\r\n")
                                .append("Имя заявки:").append(items.get(1).getName()).append(" Описание заявки:")
                                .append(items.get(1).getDesc()).append(" Код заявки:").append(items.get(1).getId()).append("\r\n")
                                .append("Имя заявки:").append(items.get(2).getName()).append(" Описание заявки:")
                                .append(items.get(2).getDesc()).append(" Код заявки:").append(items.get(2).getId()).append("\r\n")
                                .append("Имя заявки:").append(items.get(3).getName()).append(" Описание заявки:")
                                .append(items.get(3).getDesc()).append(" Код заявки:").append(items.get(3).getId()).append("\r\n")
                                .append("---------------------------------------------------------\r\n")
                                .append("0. Добавить заявку\r\n")
                                .append("1. Посмотреть существующие заявки\r\n")
                                .append("2. Редактировать заявку\r\n")
                                .append("3. Удаление заявки\r\n")
                                .append("4. Поиск заявки по Id\r\n")
                                .append("5. Поиск заявки по имени\r\n")
                                .append("6. Выход из меню\r\n")
                                .append("------------ Выход из меню --------------\r\n")
                                .toString()
                )
        );
        // возвращаем обратно стандартный вывод в консоль.
        System.setOut(stdout);

    } */
}

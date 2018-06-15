package ru.job4j.tracker;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartUIStreamTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void beforeOutput() {
        System.setOut(stdout);
    }

    @Test
    public void whenShowAllThenTrackerHasShowAllItems() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "desc"));
        Input input = new StubInput(new String[]{"3", "д"});
        new StartUI(input, tracker).init();
        assertThat(new String(out.toByteArray()), is(new StringBuilder()
                .append("0 : Выход.")
                .append(System.lineSeparator())
                .append("1 : Добавить новую заявку")
                .append(System.lineSeparator())
                .append("2 : Редактировать заявку.")
                .append(System.lineSeparator())
                .append("3 : Показать все заявки")
                .append(System.lineSeparator())
                .append("4 : Удалить заявку.")
                .append(System.lineSeparator())
                .append("5 : Найти заявку по идентификатору.")
                .append(System.lineSeparator())
                .append("6 : Найти заявку по имени.")
                .append(System.lineSeparator())
                .append("-----------Проссмотр всех заявок-----------")
                .append(System.lineSeparator())
                .append("-----------Заявка с идентификатором:" + item.getId() + "-----------")
                .append(System.lineSeparator())
                .append("Имя заявки :name")
                .append(System.lineSeparator())
                .append("Описание заявки :desc")
                .append(System.lineSeparator())
                .toString()));
    }

    @Test
    public void whenFindByNameThanTrackerHasShowAllItemThisName() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "desc"));
        Input input = new StubInput(new String[]{"6", "name", "д"});
        new StartUI(input, tracker).init();
        assertThat(new String(out.toByteArray()), is(new StringBuilder()
                .append("0 : Выход.")
                .append(System.lineSeparator())
                .append("1 : Добавить новую заявку")
                .append(System.lineSeparator())
                .append("2 : Редактировать заявку.")
                .append(System.lineSeparator())
                .append("3 : Показать все заявки")
                .append(System.lineSeparator())
                .append("4 : Удалить заявку.")
                .append(System.lineSeparator())
                .append("5 : Найти заявку по идентификатору.")
                .append(System.lineSeparator())
                .append("6 : Найти заявку по имени.")
                .append(System.lineSeparator())
                .append("-----------Поиск заявки по имени.-----------")
                .append(System.lineSeparator())
                .append("Заявка с именем :name найдена :")
                .append(System.lineSeparator())
                .toString()));
    }
    @Test
    public void whenFindByIdThanTrackerItemThisId() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name", "desc"));
        Input input = new StubInput(new String[]{"5", item.getId(), "д"});
        new StartUI(input, tracker).init();
        assertThat(new String(out.toByteArray()), is(new StringBuilder()
                .append("0 : Выход.")
                .append(System.lineSeparator())
                .append("1 : Добавить новую заявку")
                .append(System.lineSeparator())
                .append("2 : Редактировать заявку.")
                .append(System.lineSeparator())
                .append("3 : Показать все заявки")
                .append(System.lineSeparator())
                .append("4 : Удалить заявку.")
                .append(System.lineSeparator())
                .append("5 : Найти заявку по идентификатору.")
                .append(System.lineSeparator())
                .append("6 : Найти заявку по имени.")
                .append(System.lineSeparator())
                .append("-----------Поиск заявки по идентификатору-----------")
                .append(System.lineSeparator())
                .append("Заявка с идентификатором :" + item.getId() + " найдена :")
                .append(System.lineSeparator())
                .append("Имя заявки :name")
                .append(System.lineSeparator())
                .append("Описание заявки :desc")
                .append(System.lineSeparator())
                .toString()));
    }
}
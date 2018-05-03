package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.modules.Tracker;
import ru.job4j.tracker.modules.Items;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Kaleganov
 * @version StubInputTest FINAL REFACTORING 4/0
 * допилил тесты  чтобы метод по созданию готового трекера не простаивал
 */

public class StubInputTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final Items in1 = new Items("his", "desc");
    private final Items in2 = new Items("name", "desc2");

    @Before
    public void loadOutput()  {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }

    /**
     * возвращает готовый список заявок
     * @return
     */
    public Tracker trackerReturn() {
        Tracker tracker = new Tracker();
        tracker.add(this.in1);
        tracker.add(this.in2);
        return tracker;
    }

    /**
     * готовый ввод данных
     * @param str
     * @return
     */
    public Input inputReturn(String[] str) {
        Input input = new StubInput(str);
        return input;
    }

    /**
     * возвращает готовое меню
     * @return
     */
    public String retunMenu() {
        return new StringBuilder()
                .append("0. Add new Item")
                .append(System.lineSeparator())
                .append("1. Show all items")
                .append(System.lineSeparator())
                .append("2. Edit item")
                .append(System.lineSeparator())
                .append("3. Delete item")
                .append(System.lineSeparator())
                .append("4. Find item by Id")
                .append(System.lineSeparator())
                .append("5. Find items by name")
                .append(System.lineSeparator())
                .append("6. Add comment by Items")
                .append(System.lineSeparator())
                .append("7. Exit Program")
                .append(System.lineSeparator())
                .toString();
    }
    /**
     * тест добавления заявки
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = trackerReturn();     // создаём Tracker
        new StartUI(tracker, inputReturn(new String[]{"0", "test name", "desc", "7"})).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findAll().get(2).getName(), is("test name")); // проверяем, что 3 элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    /**
     * тестирование вывода списка всех заявок
     */
    @Test
    public void whenshouAllThenTrackshouall() {
        new StartUI(inputReturn(new String[]{"1", "7"}), trackerReturn()).init();
        assertThat(new String(this.out.toByteArray()), is(new StringBuilder()
                        .append(retunMenu())
                        .append(this.in1)
                        .append(System.lineSeparator())
                        .append(this.in2)
                        .append(System.lineSeparator())
                        .append(retunMenu())
                        .toString()
                )
        );
    }

    /**
     * тестирование вывода в консоль заявки по id
     */
    @Test
    public void whenFindbyIDitemsThenTrackFindbyID() {
        new  StartUI(trackerReturn(), inputReturn(new String[]{"4", trackerReturn().findAll().get(1).getId(), "7"})).init();
        assertThat(new String(this.out.toByteArray()), is(new StringBuilder()
                        .append(retunMenu())
                        .append(this.in2)
                        .append(System.lineSeparator())
                        .append(retunMenu())
                        .toString()
                )
        );
    }
    /**
     * тестирование вывода в консоль заявк по имени
     */
    @Test
    public void whenFindbyNAMEitemsThenTrackFindbyNAME() {
        new StartUI(trackerReturn(), inputReturn(new String[]{"5", in2.getName(), "7"})).init();
        assertThat(new String(this.out.toByteArray()), is(new StringBuilder()
                        .append(retunMenu())
                        .append(this.in2)
                        .append(System.lineSeparator())
                        .append(retunMenu())
                        .toString()
                )
        );
    }

    /**
     * тест изменения заявки по id
     */
    @Test
    public void whenShouEditThenTrackerHashEdi() {
        Tracker track = trackerReturn();
        new StartUI(track, inputReturn(new String[]{"2", track.findAll().get(0).getId(), "test name", "desc new", "7"})).init();
        assertThat(track.findAll().get(0).getName(), is("test name"));
    }

    /**
     * проверка метода удаления заявки
     */
    @Test
    public void whenshouDELETitemsThenTrackDELET() {
        int expected = 1;
        Tracker tracker = trackerReturn();
        new StartUI(inputReturn(new String[]{"3", tracker.findAll().get(0).getId(), "7"}), tracker).init();
        assertThat(tracker.findAll().size(), is(expected));
    }
}
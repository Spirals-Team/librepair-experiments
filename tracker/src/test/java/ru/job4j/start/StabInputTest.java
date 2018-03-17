package ru.job4j.start;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.job4j.tracker.connection.ConnectionSQL;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.StubInput;
import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.start.StartUi;
import ru.job4j.tracker.start.Tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 06.09.17;
 * @version $Id$
 * @since 0.1
 */
public class StabInputTest {
    @Mock
    private Tracker tracker;
    private Item item;
    private Item res;
    @Before
    public void init() {
        this.item = new Item("test name", "desc");
        this.res = new Item("1", "test name", "desc");
        Mockito.when(tracker.add(item)).thenReturn(res);

    }
    /**
     * Тест добавления нового элемента.
     */
    @Test
    @Ignore
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        when(tracker.getAll()).thenReturn(new ArrayList<Item>(Arrays.asList(res)));
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUi(input, tracker).init();
        //   создаём StartUI и вызываем метод init()
   //     tracker = new Tracker();
        Item item = tracker.getAll().get(0);
        assertThat(item.getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        tracker.delete(item);
        tracker.close();
    }

    /**
     * Тест метода edit изменяем существующий элемент.
     */
    @Test
    @Ignore
    public void whenUpdateThenTrackerHasUpdatedValue() {
        //Напрямую добавляем заявку
        Item item1 = tracker.add(this.item);
        when(tracker.findById(item1.getId())).thenReturn(item1);
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"2", item1.getId(), "test", "desc", "6"});
        new StartUi(input, tracker).init();
        verify(tracker).add(this.item);
        verify(tracker).findById(item1.getId());
        item1.setName("test");
        verify(tracker).update(item1);
        // создаём StartUI и вызываем метод init()

//        tracker = new Tracker();
//        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
//        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
//        tracker.delete(item);
//        tracker.close();
    }


    /**
     * Тест метода deleteItem.
     */
    @Test
    @Ignore
    public void whetTrackerHasTwoItemsThenHasOneItem() {
        Tracker tracker = new Tracker(new ConnectionSQL());
        Item item = new Item("test1", "testDesc1");
        Item item1 = new Item("test2", "testDesc2");
        item = tracker.add(item);
        item1 = tracker.add(item1);
        Input input = new StubInput(new String[]{"3", item.getId(), "6"});
        new StartUi(input, tracker).init();
        tracker = new Tracker(new ConnectionSQL());
        List<Item> list = new ArrayList<>();
        list.add(item1);
        assertThat(list, is(tracker.getAll()));
        tracker.delete(item);
        tracker.delete(item1);
        tracker.close();
    }

    /**
     * Test findById.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemThenReturnById() {
        Tracker tracker = new Tracker(new ConnectionSQL());
        Item item = new Item("test1", "testDesc1");
        Item item1 = new Item("test2", "testDesc2");
        item = tracker.add(item);
        item1 = tracker.add(item1);
        Input input = new StubInput(new String[]{"4", item1.getId(), "3", "6"});
        new StartUi(input, tracker).init();
        tracker = new Tracker(new ConnectionSQL());
        Item ex = tracker.findById(item.getId());
        assertThat(ex.getName(), is("test1"));
        tracker.delete(item);
        tracker.delete(item1);
        tracker.close();
    }

    /**
     * Test findByName.
     */
    @Test
    @Ignore
    public void whenTrackerHasTwoItemThenReturnByName() {
        Tracker tracker = new Tracker(new ConnectionSQL());
        Item item = new Item("test1", "testDesc1");
        Item item1 = new Item("test2", "testDesc2");
        item = tracker.add(item);
        item1 = tracker.add(item1);
        Input input = new StubInput(new String[]{"5", item1.getName(), "6"});
        new StartUi(input, tracker).init();
        tracker = new Tracker(new ConnectionSQL());
        List<Item> ex = new ArrayList<>(Arrays.asList(item1));
        assertThat(tracker.findByName(item1.getName()), is(ex));
        tracker.delete(item);
        tracker.delete(item1);
        tracker.close();
    }

    /**
     * Метод проверяет последовательность вызова методов с соответствующими аргументами при удалении заявки.
     */
    @Test
    @Ignore
    public void whenWasCallDeleteMethod() {
        Item add = this.tracker.add(this.item);
        when(tracker.findById(add.getId())).thenReturn(add);
        Input input = new StubInput(new String[]{"3", add.getId(), "6"});
        new StartUi(input, tracker).init();
        verify(tracker).add(item);
        verify(tracker).findById(add.getId());
        verify(tracker).delete(add);
        verify(tracker).close();
        verifyNoMoreInteractions(tracker);
    }

    /**
     * Тест проверяет последовательность вызова методов
     * при поиске заявки по id.
     */
    @Test
    @Ignore
    public void whenWasCallFindByIdMethod() {
        when(tracker.findById(res.getId())).thenReturn(res);
        Input input = new StubInput(new String[]{"4", res.getId(), "3", "6"});
        new StartUi(input, tracker).init();
        verify(tracker).findById(res.getId());
        verify(tracker).close();
        verifyNoMoreInteractions(tracker);
    }

    /**
     * Тест проверяет последовательность вызова методов с соответствующими аргументами
     * при вызове findByName метода.
     */
    @Test
    @Ignore
    public void whenWasCallFindByNameMethod() {
        when(tracker.findByName(res.getName())).thenReturn(new ArrayList<Item>(Arrays.asList(res)));
        Input input = new StubInput(new String[]{"5", res.getName(), "6"});
        new StartUi(input, tracker).init();
        verify(tracker).findByName(res.getName());
        verify(tracker).close();
        verifyNoMoreInteractions(tracker);
    }
}

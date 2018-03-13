package ru.job4j.start;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * sql added 26.01.2018
 */
public class StubInputTest {
    /**
     *
     */
    @Ignore
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        tracker.initTracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUi(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.getAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        tracker.initTracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item());


        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"1", "2", item.getId(), "test name", "desc", "1", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUi(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void whenDeleteThenTrackerHasDeletedItem() {
        Tracker tracker = new Tracker();
        tracker.initTracker();

        Input input = new StubInput(new String[] {"0", "nameForItemDelete", "description1", "1", "0", "nameForItemDelete", "description2", "1",
                                                     "0", "name3", "description3", "1", "6"});
        new StartUi(input, tracker).init();

        ArrayList<Item> itemsForDeleting = tracker.findByName(new String("nameForItemDelete"));

        String[] operatorsForDeleting = new String[itemsForDeleting.size() * 2 + 1];

        Integer k = 0;

        for (Integer index = 0; index < itemsForDeleting.size(); index++) {

            String idOfTheItem = itemsForDeleting.get(index).getId();

            operatorsForDeleting[k] = "3";
            k = k + 1;
            operatorsForDeleting[k] = idOfTheItem;
            k = k + 1;
        }
        operatorsForDeleting[k] = "6";
        Input input2 = new StubInput(operatorsForDeleting);
        new StartUi(input2, tracker).init();

        assertThat(tracker.findAll()[0].getName(), is("name3"));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void whenFindByIdThenHasItemWhichWeCanEditForExampleAndCanSeeThanInConsole() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Input input = new StubInput(new String[] {"0", "name1", "description1", "1", "0", "name2", "description2", "1",
                "0", "name3", "description3", "1", "6"});
        new StartUi(input, tracker).init();

        String idOfThirdItem = tracker.findByName("name3").get(0).getId();

        Input input2 = new StubInput(new String[] {"1", "4", idOfThirdItem, "2", idOfThirdItem, "whenFindByIdThenHasItemName", "whenFindByIdThenHasItemDesc", "1", "6"});

        new StartUi(input2, tracker).init();

        assertThat(tracker.findById(idOfThirdItem).getName(), is("whenFindByIdThenHasItemName"));

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void whenFindItemsByNameThenWeCanSeeInConsole() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Input input = new StubInput(new String[] {"0", "nameForFind", "description0", "1", "0", "name1", "description1", "1", "0", "name2", "description2", "1",
                "0", "name3", "description3", "1", "0", "nameForFind", "description4", "1", "5", "nameForFind", "6"});
        new StartUi(input, tracker).init();

        tracker.trackerSQL.deleteAllItemsFromBase();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void whenExitProgramThenWeSeeThat() {
        Tracker tracker = new Tracker();
        tracker.initTracker();
        Input input = new StubInput(new String[] {"0", "name1", "description1", "1", "6"});
        new StartUi(input, tracker).init();

        tracker.trackerSQL.deleteAllItemsFromBase();
    }
}

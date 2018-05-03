package ru.job4j.tracker.modules;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerTest {

    @Test
    public void testirovanieTrackerADD() { //проверка метода add
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        tracker.add(items);
        tracker.add(items1);
        Items expected = tracker.findAll().get(0);
        assertThat(expected, is(items));
    }

    @Test
    public void testirovanieTrackerReplace() { //проверка метода изменения заявки
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        tracker.add(items);
        tracker.add(items1);
        Tracker tracker1 = new Tracker();
        Items items3 = new Items("Я твой дом труба шатал", "слышь админ я тебя найду сцуко!!!");
        Items items4 = new Items("Цой жив))", "лексир бессмертия ");
        tracker1.add(items3);
        tracker1.add(items4);
        System.out.println(tracker1.findAll().get(1));
        tracker1.replace(items3.getId(), items);
        System.out.println(tracker1.findAll().get(1));
        assertThat(tracker.findAll().get(0), is(tracker1.findAll().get(0)));
    }

    @Test
    public void testirovanieTrackerdelete() { //проверка метода удаления заявки
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        int expected = 1;
        tracker.add(items);
        tracker.add(items1);
        tracker.delete(tracker.findAll().get(1).getId());
        assertThat(tracker.findAll().size(), is(expected));
    }

    @Test
    public void testirovanieTrackerfindAll() { //проверка метода показать все заявки
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        tracker.add(items);
        tracker.add(items1);
        Tracker tracker1 = new Tracker();
        ArrayList<Items> ekepted = tracker.findAll();
        for (Items i:ekepted) {
                tracker1.add(i);
        }
        assertThat(tracker.findAll(), is(tracker1.findAll()));
    }

    @Test
    public void testirovanieTrackerById() {   // поверка метода поиск заявки по id
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        tracker.add(items);
        tracker.add(items1);
        Items res = tracker.findById(items1.getId());
        assertThat(res, is(tracker.findAll().get(1)));
    }

    @Test
    public void testirovanieTrackerByName() { // проверка метода поиска заявок  по имени(похожие заявки)
        Tracker tracker = new Tracker();
        Items items = new Items("Нужна помощь", "Ничего не работает, компьютер не запускается");
        Items items1 = new Items("Хелп", "От поддержки никакого толка");
        tracker.add(items);
        tracker.add(items1);
        Items expected = items;
        ArrayList<Items> result = tracker.findByName("Нужна");
        assertThat(result.get(0), is(expected));
    }
}
package ru.aserdyuchenko.tracker.start;

import org.junit.Test;
import ru.aserdyuchenko.tracker.models.Item;
import java.sql.SQLException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class TrackerTest {
    private Tracker tracker = new Tracker();

//    @Test
//    public void whenAddAndGetItemFromDataBase() throws SQLException {
//
//        tracker.add(new Item("Create DB", "Create DB for tracker", 123));
//        assertThat(tracker.getAll().get(0).getName(), is("Create DB"));
//    }
}
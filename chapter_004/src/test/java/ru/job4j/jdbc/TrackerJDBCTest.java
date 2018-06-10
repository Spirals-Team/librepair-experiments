package ru.job4j.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TrackerJDBCTest {

    TrackerJDBC tracker;

    @Before
    public void init() {
        tracker = new TrackerJDBC();
        Item item1 = new Item("Alex", "Alex_desc", 12L, "Alex_comment");
        Item item2 = new Item("Mike", "Mike_desc", 112L, "Mike_comment");
        Item item3 = new Item("Tom", "Tom_desc", 1112L, "Tom_comment");

        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
    }

    @Test
    public void whenCreateBDforItemThenAllOk() {
        Item res = tracker.findById(2);
        Assert.assertThat(res.getComments(), is("Mike_comment"));

        Item res1 = tracker.findByName("Tom");
        Assert.assertThat(res1.getComments(), is("Tom_comment"));

        Item itemNew = new Item("Tom_new", "Tom_desc_new", 11112L, "Tom_comment_new");
        tracker.replace("1", itemNew);
        Assert.assertThat(tracker.findByName("Tom_new").getComments(), is("Tom_comment_new"));
    }
}
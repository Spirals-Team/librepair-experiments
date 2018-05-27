package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class ItemTest {

    @Test
    public void testGetName() {
        String expect = "Name";
        Item newItem = new Item(expect, "Desc");
        String rsl = newItem.getName();
        assertThat(rsl, is(expect));
    }

    @Test
    public void testGetDesc() {
        String expect = "Desc";
        Item newItem = new Item("name", expect);
        String rsl = newItem.getDesc();
        assertThat(rsl, is(expect));
    }

    @Test
    public void testGetId() {
        Item newItem = new Item("Name", "Desc");
        String expect = "12345";
        newItem.setId(expect);
        String rsl = newItem.getId();
        assertThat(rsl, is(expect));
    }

    @Test
    public void testSetId() {
        Item newItem = new Item("Name", "Desc");
        String expect = "12345";
        newItem.setId(expect);
        String rsl = newItem.getId();
        assertThat(rsl, is(expect));
    }

    @Test
    public void testGetCreated() {
        Item newItem = new Item("Name", "Desc");
        long newTime = System.currentTimeMillis() + 1;
        long oldTime = newItem.getCreated();
        boolean rsl = newTime > oldTime ? true : false;
        boolean expect = true;
        assertThat(rsl, is(expect));
    }

    @Test
    public void testGetComments() {
        Item newItem = new Item("Name", "Desc");
        String[] expect = {"Test"};
        newItem.setComments(expect);
        String[] rsl = newItem.getComments();
        assertThat(rsl, is(expect));
    }

    @Test
    public void testSetComments() {
        Item newItem = new Item("Name", "Desc");
        String[] expect = {"Test"};
        newItem.setComments(expect);
        String[] rsl = newItem.getComments();
        assertThat(rsl, is(expect));
    }
}
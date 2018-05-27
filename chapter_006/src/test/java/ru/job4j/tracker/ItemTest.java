package ru.job4j.tracker;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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
    public void testGetComments() {
        Item newItem = new Item("Name", "Desc");
        List<String> list = new LinkedList<>();
        list.add("Test");
        newItem.setComments(list);
        String expect = "Test";
        String rsl = newItem.getComments().get(0);
        assertThat(rsl, is(expect));
    }

    @Test
    public void testSetComments() {
        Item newItem = new Item("Name", "Desc");
        List<String> list = new LinkedList<>();
        list.add("Test");
        newItem.setComments(list);
        list = new LinkedList<>();
        list.add("Test 2");
        newItem.setComments(list);
        String expect = "Test 2";
        String rsl = newItem.getComments().get(0);
        assertThat(rsl, is(expect));
    }
}
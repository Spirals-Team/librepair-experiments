package ru.aserdyuchenko.map.task_8;

import org.junit.Test;
import java.util.Iterator;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class CatalogTest {
    private Catalog<Integer, String> catalog = new Catalog<>();
    private Iterator<String> iterator = catalog.iterator();

    @Test
    public void whenAddNewElement() {
        Boolean result = catalog.insert(0, "First");
        assertThat(result, is(true));
    }

    @Test
    public void whenAddNewElementAndGetHim() {
        catalog.insert(0, "First");
        String result = catalog.get(0);
        assertThat(result, is("First"));
    }

    @Test
    public void whenAddNewElementAndGetHimUseIterator() {
        catalog.insert(0, "First");
        String result = iterator.next();
        assertThat(result, is("First"));
    }

    @Test
    public void whenDeleteElement() {
        catalog.insert(0, "First");
        Boolean result = catalog.delete(0);
        assertThat(result, is(true));
    }

    @Test
    public void whenAddTwoSameElementAndCheckSecondElementUseIterator() {
        catalog.insert(0, "First");
        catalog.insert(0, "First");
        iterator.next();
        Boolean result = iterator.hasNext();
        assertThat(result, is(false));
    }
}
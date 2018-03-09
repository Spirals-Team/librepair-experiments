package ru.aserdyuchenko.list.task_5_3_2;

import org.junit.Test;
import java.util.Iterator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class MyOwnLinkedListTest {
    /**
     * @param list - list.
     */
    private MyOwnLinkedList<String> list = new MyOwnLinkedList<>();

    /**
     * Test. Add and get.
     */
    @Test
    public void whenAddAndGetValue() {
        list.add("OK");
        String result = list.get(0);
        assertThat(result, is("OK"));
    }

    /**
     * Test. Add and get.
     */
    @Test
    public void whenAddThreeValueAndGetThird() {
        list.add("OK1");
        list.add("OK2");
        list.add("OK3");
        String result = list.get(2);
        assertThat(result, is("OK3"));
    }
    /**
     * Test iterator. Get next.
     */
    @Test
    public void whenGetNextUseIterator() {
        list.add("OK");
        Iterator iterator = list.iterator();
        Object result = iterator.next();
        assertThat(result, is("OK"));
    }

    /**
     * Test iterator. Check hasNext.
     */
    @Test
    public void whenCheckHasNextUseIterator() {
        list.add("OK");
        Iterator iterator = list.iterator();
        boolean result = iterator.hasNext();
        assertThat(result, is(true));
    }

    /**
     * Test iterator. When hasNext is false.
     */
    @Test
    public void whenHasNextIsFasle() {
        list.add("OK");
        Iterator iterator = list.iterator();
        iterator.next();
        boolean result = iterator.hasNext();
        assertThat(result, is(false));
    }
}
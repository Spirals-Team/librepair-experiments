package ru.aserdyuchenko.set.task_3;

import org.junit.Test;
import java.util.Iterator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class MyHashTableTest {
    private MyHashTable<String> hashTable = new MyHashTable<>();
    private Iterator iterator = hashTable.iterator();

    /**
     * Add 2 origin elements.
     */
    @Test
    public void whenAddTwoOriginElementsAndGetSecondElementUsingNext() {
        hashTable.add("Ok");
        hashTable.add("Okey");

        iterator.next();
        String result = (String) iterator.next();

        assertThat(result, is("Okey"));
    }

    /**
     * Add 2 origin element and check hasNext.
     */
    @Test
    public void whenAddTwoOriginElementAndCheckHasNext() {
        hashTable.add("Ok");
        hashTable.add("Okey");

        iterator.next();
        boolean result = iterator.hasNext();

        assertThat(result, is(true));
    }

    /**
     * Add 2 not origin element, and check hasNext.
     */
    @Test
    public void whenAddTwoNotOriginElementAndCheckHasNext() {
        hashTable.add("Ok");
        hashTable.add("Ok");

        iterator.next();
        boolean result = iterator.hasNext();

        assertThat(result, is(false));
    }
}
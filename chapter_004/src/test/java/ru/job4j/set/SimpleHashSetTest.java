package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleHashSetTest {

    @Test
    public void whenAddElementThenSimpleHashSetTheElement() {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("first");
        assertThat(set.contains("first"), is(true));
    }

    @Test
    public void whenRemoveExistingElementThenSimpleHashSetDoesNotHaveTheElement() {
        SimpleHashSet<Integer> set = new SimpleHashSet<>();
        set.add(564);
        set.remove(564);
        assertThat(set.contains(564), is(false));
    }

    @Test
    public void whenAddTheSameElementThenSimpleHashSetDoesNotHaveDuplicate() {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("first");
        set.add("first");
        set.add("first");
        assertThat(set.remove("first"), is(true));
        assertThat(set.remove("first"), is(false));
    }
}
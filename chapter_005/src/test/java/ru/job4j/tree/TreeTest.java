package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TreeTest {

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation() {

        Tree<String> tree = new Tree<>();
        tree.add("first", "second");
        tree.add("first", "third");
        tree.add("fore", "third");
        tree.add("fore", "five");
        tree.add("fore", "six");
        tree.add("six", "seven");
        tree.add("six", "five");

        Iterator<String> it = tree.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("second"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("third"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("five"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("six"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("seven"));
        it.next();
    }

    @Test
    public void whenTreeIsBinaryThenReturnTrue() {
        Tree<String> tree = new Tree<>();
        tree.add("first", "second");
        tree.add("first", "third");
        tree.add("second", "fore");
        tree.add("second", "five");
        tree.add("third", "six");
        tree.add("third", "seven");

        assertThat(tree.isBinary(), is(true));
    }

    @Test
    public void whenTreeIsNotBinaryThenReturnFalse() {
        Tree<Integer> tree = new Tree<>();
        tree.add(50, 70);
        tree.add(50, 80);
        tree.add(70, 90);
        tree.add(70, 100);
        tree.add(90, 110);
        tree.add(90, 120);
        tree.add(100, 180);
        tree.add(100, 190);
        tree.add(100, 290);
        assertThat(tree.isBinary(), is(false));
    }
}
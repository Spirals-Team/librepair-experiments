package ru.aserdyuchenko.controlQuestions;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class MyOwnLinkedListTest {
    private MyOwnLinkedList<Integer> list = new MyOwnLinkedList<>();

    @Test
    public void whenOriginList() {
        list.add(1);
        list.add(2);
        list.add(3);

        List<Integer> assertList = Arrays.asList(1, 2, 3);
        assertThat(list.toArray(), is(assertList.toArray()));
    }

    @Test
    public void whenMirrorList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.mirror();

        List<Integer> assertList = Arrays.asList(3, 2, 1);
        assertThat(list.toArray(), is(assertList.toArray()));
    }
}
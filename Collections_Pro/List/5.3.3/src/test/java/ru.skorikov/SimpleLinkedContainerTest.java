package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 28.12.17
 * @ version: java_kurs_standart
 */
public class SimpleLinkedContainerTest {
    /**
     * Проверяем итератор.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetNextElement() throws Exception {
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        Iterator iterator = container.iterator();

        Assert.assertThat(iterator.hasNext(), is(false));
    }
    /**
     * Проверяем итератор, получим следующий элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetNextElementFromContainer() throws Exception {
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        container.add("String");
        Iterator iterator = container.iterator();

        Assert.assertThat(iterator.next().toString(), is("String"));
    }

}
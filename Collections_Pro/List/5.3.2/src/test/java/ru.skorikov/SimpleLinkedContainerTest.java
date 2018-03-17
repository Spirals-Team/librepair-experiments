package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 26.10.17
 * @ version: java_kurs_standart
 */
public class SimpleLinkedContainerTest {
    /**
     * Для тестирования исключений.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /**
     * Пробуем добавить элемент в контейнер.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryAddElementToContainer() throws Exception {
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        container.add("string1");
        container.add("string2");

        assertNotNull(container);
    }

    /**
     * Проверим что добавлено то, что нужно.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryAddElementToContainerEndGetResult() throws Exception {
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        String str = "testString";
        container.add(str);
        String testString = container.get(0);

        Assert.assertEquals(testString, "testString");
    }

    /**
     * Проверим что контейнер пустой.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenContainerNullWhenReturnNull() throws Exception {
        testException.expect(NoSuchElementException.class);
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();

        Assert.assertNull(container.get(0));
    }

    /**
     * Пробуем получить элемент контейнера.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetElementFromContayner() throws Exception {
        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        container.add("string1");
        container.add("string2");

        String testString = container.get(1);

        Assert.assertEquals(testString, "string2");
    }

    /**
     * Пробуем получить несуществующий элемент контейнера.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenElementNoInContaynerWhenException() throws Exception {
        testException.expect(NoSuchElementException.class);

        SimpleLinkedContainer<String> container = new SimpleLinkedContainer<>();
        container.add("string1");
        container.add("string2");
        container.get(3);
    }
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
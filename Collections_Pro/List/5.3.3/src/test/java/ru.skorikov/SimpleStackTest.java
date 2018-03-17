package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 29.10.17
 * @ version: java_kurs_standart
 */
public class SimpleStackTest {
    /**
     * Для обработки ошибок.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /**
     * Пробуем добавить элемент в коллекцию.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElenentWhenReturnElement() throws Exception {

        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("String1");
        stack.push("String2");

        String testString = "String2";
        String element = (String) stack.getContainer().get(1);

        Assert.assertEquals(testString, element);
    }

    /**
     * Проверяем что последний добавленный элемент удален..
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElementAndPollWhenReternNull() throws Exception {

        testException.expect(NoSuchElementException.class);
        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("String1");
        stack.push("String2");

        stack.poll();
        stack.getContainer().get(1);
    }

    /**
     * Проверяем что предпоследний добавленный элемент теперь первый в стеке.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElementAndPollWhenReternNewFirst() throws Exception {

        SimpleStack<String> stack = new SimpleStack<>();
        stack.push("String1");
        stack.push("String2");

        stack.poll();
        String testString = (String) stack.getContainer().get(0);

        Assert.assertEquals(testString, "String1");
    }

    /**
     * Проверим что последний элемент удаляется.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenRemoveAllWhenNotReturnNPE() throws Exception {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.poll(), is(3));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }

}
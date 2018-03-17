package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 29.10.17
 * @ version: java_kurs_standart
 */
public class SimpleQueueTest {
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
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);

        Integer testInt = (Integer) queue.getContainer().get(1);

        Assert.assertThat(testInt, is(2));
    }

    /**
     * Проверяем что первый добавленный элемент удален..
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElementAndPollWhenReternNull() throws Exception {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);

        queue.poll();
        Integer testInt = (Integer) queue.getContainer().get(0);

        Assert.assertThat(testInt, is(2));
    }

    /**
     * Проверяем что второй добавленный элемент теперь первый в очереди.
     *
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElementAndPollWhenReternNewFirst() throws Exception {

        SimpleQueue<String> queue = new SimpleQueue<>();
        queue.push("String1");
        queue.push("String2");

        queue.poll();
        String testString = (String) queue.getContainer().get(0);

        Assert.assertEquals(testString, "String2");
    }

    /**
     * Проверим возвращаемое значение.
     * @throws Exception исключение.
     */
    @Test
    public void thenAddElementAndPoll() throws Exception {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        Assert.assertThat(queue.poll(), is(1));
    }

}
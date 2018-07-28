package ru.job4j.wait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleBlockingQueueTest {
    private PrintStream stdout = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void backOut() {
        System.setOut(stdout);
    }

    @Test
    public void testConcurrentThread() throws Exception {
        int limit = 2; // ограничение очереди
        Integer[] addItem = new Integer[] {1, 2, 3, 4, 5}; // массив элементов для вставки в очередь
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(limit);

        Consumer<Integer> consumer = new Consumer<>(queue, addItem.length);
        Producer<Integer> producer = new Producer<>(queue, addItem);

        consumer.join();
        producer.join();

        int size = 0; //количество элементов в очереди
        Set<Integer> offer = new HashSet<>(); //хранилище добавленых элементов
        Set<Integer> poll = new HashSet<>(); //хранилище извлеченых элементов
        String[] strings = new String(out.toByteArray()).split(System.lineSeparator());

        for (String item : strings) {
            String[] cond = item.split(" ");
            if (cond[0].equals("Offer:")) {
                offer.add(Integer.parseInt(cond[1]));
                size++;
                assertTrue(size <= limit);
            }
            if (cond[0].equals("Poll:")) {
                poll.add(Integer.parseInt(cond[1]));
                size--;
                assertTrue(size >= 0);
            }
        }

        assertThat(strings[0].lastIndexOf("Offer:"), is(0)); //В начале очередь пустая. В пустую очередь может произойти только добавление
        assertTrue(Arrays.equals(offer.toArray(), addItem));
        assertTrue(Arrays.equals(poll.toArray(), addItem));
    }
}
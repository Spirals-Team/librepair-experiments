package ru.job4j.jmm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class MyThreadTest {

    /**
     * При правильном выполнении программы на консоль
     * должно было выводиться:
     * [first]
     * [second]
     * [third]
     * Однако в данной версии программы потоки
     * "сражаются" за квант времени, что приводит к неверному
     * выводу в консоль.
     * @throws InterruptedException
     */
    @Test
    public void testPrint() throws InterruptedException {
        Text text = new Text();
        new MyThread(text, "first");
        new MyThread(text, "second");
        new MyThread(text, "third");
        Thread.sleep(1000);
    }
}
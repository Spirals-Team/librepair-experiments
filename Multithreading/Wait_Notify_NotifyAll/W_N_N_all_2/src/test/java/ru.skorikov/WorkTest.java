package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 08.02.18
 * @ version: java_kurs_standart
 */
public class WorkTest {
    /**
     * Проверим класс Работа.
     * Выводит в консоль текст.
     */
    @Test
    public void tryPrintString() {
        PrintStream console = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        Work work = new Work();
        work.run();
        String result = outputStream.toString();
        System.setOut(console);
        Assert.assertEquals(result, "It's work 1\n");
    }
}
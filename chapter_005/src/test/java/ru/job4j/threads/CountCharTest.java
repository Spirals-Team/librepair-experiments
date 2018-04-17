package ru.job4j.threads;

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
public class CountCharTest {
    private String path = "..\\.gitignore";
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenTimeEndedFirstly() {
        new CountWithTime(path, 100000).go();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Подсчет слов - ЗАПУСК")
                                .add("Таймер - ЗАПУСК")
                                .add("Общее количество символов: 318")
                                .add("Главные поток - ЗАВЕРШЕНИЕ").toString()
                )
        );
    }

    @Test
    public void whenCounterEndedFirtly() {
        new CountWithTime(path, 2000).go();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Подсчет слов - ЗАПУСК")
                                .add("Таймер - ЗАПУСК")
                                .add("Время истекло")
                                .add("Подсчет слов - ЗАВЕРШЕНИЕ")
                                .add("Главные поток - ЗАВЕРШЕНИЕ").toString()
                )
        );
    }
}
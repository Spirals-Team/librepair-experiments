package ru.job4j.threads;

import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс для тестирования поиска файлов
 */
public class ParallelSearchTest {

    @Test
    /**
     * Метод тестирует поиск файлов с конкретными параметрами
     */
    public void testParallelSearch() {
        ParallelSearch ps = new ParallelSearch("d:\\temp\\1111", "Fli",  Arrays.asList("ics","iii"));
        assertThat(ps.isFinish(), is(false));
        ps.init();
        while (!ps.isFinish()) {

        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(ps.isFinish(), is(true));
        System.out.println(String.format("Result - %s files found", ps.result().size()));
        ps.result().stream().forEach((s) -> System.out.println(s));
    }

}
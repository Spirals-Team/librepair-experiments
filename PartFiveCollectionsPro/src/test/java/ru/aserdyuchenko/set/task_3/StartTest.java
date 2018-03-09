package ru.aserdyuchenko.set.task_3;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class StartTest {
    /**
     * Тестирование времени на добавление новых эллементов.
     */
    @Test
    public void whenFindPerformanceOfAdd() {
        Start start = new Start();

        long resultForHashTable = start.addInHashTable("qwert", 10000);
        long resultForSimpleSet = start.addInSimpleSet("qwert", 10000);
        boolean result = resultForHashTable < resultForSimpleSet;

        assertThat(result, is(true));
    }
}
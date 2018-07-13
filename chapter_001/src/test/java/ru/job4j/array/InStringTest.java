package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InStringTest {

    /**
     * Проверка на вхождение
     * Ожидаемый ответ - да
     */
    @Test
    public void whenSubHasSubstringOriginThenTrue() {
        String origin = "Привет";
        String sub = "иве";
        InString ss = new InString();
        assertThat(ss.contains(origin, sub), is(true));

    }
    /**
     * Проверка на вхождение
     * Ожидаемый ответ - нет
     */
    @Test
    public void whenSubNotHasSubstringOriginThenFalse() {
        String origin = "Привет";
        String sub = "ива";
        InString ss = new InString();
        assertThat(ss.contains(origin, sub), is(false));

    }

}

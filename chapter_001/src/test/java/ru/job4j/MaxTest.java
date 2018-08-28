package ru.job4j;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class MaxTest.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class MaxTest {
    /**
     * Method whenFirstLessSecond. Тестируем метод max.
     */
    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        int result = maxim.max(1, 2);
        assertThat(result, is(2));
    }

    /**
     * Method maximumOfThree. Тестируем метод max с тремя параметрами.
     */
    @Test
    public void maximumOfThree() {
        Max maxim = new Max();
        int result = maxim.max(1, 5, 3);
        assertThat(result, is(5));
    }
}

package ru.job4j.collections.iterator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 30.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class EvenItTest {
    /**
     * Дёргаем next в цикле.
     */
    @Test
    public void whenToArrayAddedThreeElements() {
        EvenIt it = new EvenIt(new int[]{4, 2, 1, 1, 6});
        String result = "";
        while (it.hasNext()) {
            result += it.next();
        }
        String ex = "426";
        assertThat(result, is(ex));
    }

    /**
     *Дёргаем next в ручную.
     */
    @Test
    public void whenArrayHasNotElementsEven() {
        EvenIt it = new EvenIt(new int[]{4, 2, 1, 1});
        it.next();
        it.next();
        boolean result = it.hasNext();
        assertThat(result, is(false));
    }
}
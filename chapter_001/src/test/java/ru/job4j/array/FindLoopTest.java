package ru.job4j.array;
/**
 * @autor Alexander Kaleganov
 * @version 1/001
 * @since 02.02.2018
 */

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FindLoopTest {
    @Test
    public void proverkaViborkaMass1() {
        int[] arrmass = new int[]{1, 2, 3, 4, 5, 6};
        int result = new FindLoop().indexOf(arrmass, 3);
        assertThat(result, is(2)); //елемент найден, значит метод должен вернуть индекс 2
    }
    @Test
    public void proverkaViborkaMass2() {
        int[] arrmass = new int[]{1, 2, 10, 4, 5, 6};
        int result = new FindLoop().indexOf(arrmass, 3);
        assertThat(result, is(-1)); // элемент не найден, метод вернул число  -1
    }
}

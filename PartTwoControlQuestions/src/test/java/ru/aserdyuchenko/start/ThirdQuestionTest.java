package ru.aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для класса ThirdQuestion.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class ThirdQuestionTest {
/**
 * When you want get array.
 */
    @Test
    public void whenYouWantEditArray() {
        ThirdQuestion example = new ThirdQuestion();
        int[] showArray = example.getARRAY();
        assertThat(showArray, is(new int[]{1, 2}));
     }
}

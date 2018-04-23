package ru.aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для класса FourthQuestion.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class FourthQuestionTest {
/**
 * When you want check array. And array true.
 */
    @Test
    public void whenArrayRight() {
        FourthQuestion check = new FourthQuestion();
        boolean result = check.checkArray(new String[]{"(", "(", ")", ")"});
        assertThat(result, is(true));
     }
/**
 * When open brackets more then close. And array false.
 */
    @Test
    public void whenArrayFalseOpenBracketsMoreThenClose() {
        FourthQuestion check = new FourthQuestion();
        boolean result = check.checkArray(new String[]{"(", "(", "(", ")"});
        assertThat(result, is(false));
     }
/**
 * When array false. In the migle brackets is ancorrect.
 */
    @Test
    public void whenArrayFalseInTheMidleBracketsAncorrect() {
        FourthQuestion check = new FourthQuestion();
        boolean result = check.checkArray(new String[]{"(", ")", ")", "(", "(", ")"});
        assertThat(result, is(false));
     }
/**
 * When array false. First bracket is close.
 */
    @Test
    public void whenArrayFalseFirstBracketCLose() {
        FourthQuestion check = new FourthQuestion();
        boolean result = check.checkArray(new String[]{")", "(", ")", "("});
        assertThat(result, is(false));
     }
/**
 * When array false. All brackets is open.
 */
    @Test
    public void whenArrayFalseAllBracketsOpen() {
        FourthQuestion check = new FourthQuestion();
        boolean result = check.checkArray(new String[]{"(", "(", "(", "("});
        assertThat(result, is(false));
     }
}

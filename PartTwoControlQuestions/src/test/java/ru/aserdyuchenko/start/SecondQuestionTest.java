package ru.aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тест для класса SecondQuestion.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.11.2016
 */
public class SecondQuestionTest {
/**
 * When you want division dy null.
 */
    @Test
    public void whenYouDivisionByNull() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
        SecondQuestion example = new SecondQuestion();
        example.exceptionExample(1, 0);
        assertThat(out.toString(), is("The arithmetic operation error."));
     }
}

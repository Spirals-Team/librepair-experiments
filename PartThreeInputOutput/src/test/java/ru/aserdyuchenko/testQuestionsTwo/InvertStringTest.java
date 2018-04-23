package ru.aserdyuchenko.testQuestionsTwo;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.PrintStream;

public class InvertStringTest {
  /**
   * Тестирование только на одном слове.
   */
  @Test
  public void whenInvertHelloByFirstMethod() {
    InvertString invertString = new InvertString();
    String newWord = invertString.invertFirstMethod("Hello");
    assertThat(newWord, is("olleH"));
  }
  /**
   * Тестирование только на одном слове.
   */
  @Test
  public void whenInvertHelloBySecondMethod() {
    InvertString invertString = new InvertString();
    String newWord = invertString.invertSecondMethod("Hello");
    assertThat(newWord, is("olleH"));
  }
  /**
   * Тестирование только на одном слове.
   */
  @Test
  public void whenInvertHelloByThirdMethod() {
    InvertString invertString = new InvertString();
    String newWord = invertString.invertThirdMethod("Hello");
    assertThat(newWord, is("olleH"));
  }
  /**
   * Тестирование всего предложения.
   */
  @Test
  public void whenInvertStringHelloWorld() throws Exception {
    String stringForInvert = "Hello World";
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    InputStream input = new ByteArrayInputStream(stringForInvert.getBytes());
    InvertString invert = new InvertString();
    invert.invertAllString(input, out);
    assertThat(out.toString(), is("olleH dlroW "));
  }
}

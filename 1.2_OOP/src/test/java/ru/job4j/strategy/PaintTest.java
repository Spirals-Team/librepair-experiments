package ru.job4j.strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Класс PaintTest.
 *@author ifedorenko
 *@since 26.08.2017
 *@version 1
 */
 public class PaintTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream stdout = System.out;

    /**
     * Before.
     */
    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    /**
     * After
     */
    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }
    /**
     * Triangle 3.
     */
     @Test
     public void whenTriangleWithHeightThreeThenStringWithThreeRows() {
         System.setOut(new PrintStream(out));
		 Paint paint = new Paint();
		 paint.draw(new Triangle(3));
         final String line = System.getProperty("line.separator");
         String expected = String.format("  x%s x x%sxxxxx%s%s", line, line, line, line);
         assertThat(out.toString(), is(expected));
	 }
    /**
     * Square 4 x 4.
     */
	 @Test
      public void whenPaintSquareWithWidthFourThenStringWithFourColsAndFourRows() {
         System.setOut(new PrintStream(out));
         Paint paint = new Paint();
         paint.draw(new Square(4));
         final String line = System.getProperty("line.separator");
         String expected = String.format("xxxx%sx  x%sx  x%sxxxx%s%s", line, line, line, line, line);
         assertThat(out.toString(), is(expected));
    }
 }
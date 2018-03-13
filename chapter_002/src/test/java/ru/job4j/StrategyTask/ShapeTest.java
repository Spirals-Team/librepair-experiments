package ru.job4j.strategytask;

import org.junit.Test;
/*import java.io.ByteArrayOutputStream;
import java.io.PrintStream;*/
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ShapeTest {

    /**
     * Test square.
     */
    @Test
    public void whenContextIsSquareThenTheSquareIsPaint() {

        ContextShape contextShape = new ContextShape(new ShapeSquare());
        String squareInString = contextShape.executeShape();
        String expectedSquareInString = "+++++     ++     +++++";
        assertThat(squareInString, is(expectedSquareInString));
    }

    /**
     * Test triangle.
     */
    @Test
    public void whenContextIsTriangleTheTrianleIsPaint() {

        ContextShape contextShape = new ContextShape(new ShapeTriangle());
        String triangleInString = contextShape.executeShape();
        String expectedTriangleInString = "   +     + +  +   + +++++++";
        assertThat(triangleInString, is(expectedTriangleInString));
    }
}

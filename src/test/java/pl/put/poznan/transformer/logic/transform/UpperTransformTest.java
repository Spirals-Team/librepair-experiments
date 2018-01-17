package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UpperTransformTest {
    private Transform transform;
    private UpperTransform upperTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.upperTransform = new UpperTransform(transform);
    }

    @Test
    public void testUpperFromLowerShouldSucceed() {
        String result = upperTransform.apply("example text");
        assertTrue(result.equals("EXAMPLE TEXT"));
    }

    @Test
    public void testUpperFromVariousShouldSucceed() {
        String result = upperTransform.apply("eXampLe tExT");
        assertTrue(result.equals("EXAMPLE TEXT"));
    }

    @Test
    public void testUpperFromUpperShouldSucceed() {
        String result = upperTransform.apply("EXAMPLE TEXT");
        assertTrue(result.equals("EXAMPLE TEXT"));
    }

}
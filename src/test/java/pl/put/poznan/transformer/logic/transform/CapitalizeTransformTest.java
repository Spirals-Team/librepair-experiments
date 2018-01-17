package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CapitalizeTransformTest {
    private Transform transform;
    private CapitalizeTransform capitalizeTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.capitalizeTransform = new CapitalizeTransform(transform);
    }

    @Test
    public void testCapitalizeFromUpperShouldSucceed() {
        String result = capitalizeTransform.apply("EXAMPLE TEXT");
        assertTrue(result.equals("Example Text"));
    }

    @Test
    public void testCapitalizeFromVariousShouldSucceed() {
        String result = capitalizeTransform.apply("eXampLe tExT");
        assertTrue(result.equals("Example Text"));
    }

    @Test
    public void testCapitalizeFromCapitalizeShouldSucceed() {
        String result = capitalizeTransform.apply("Example Text");
        assertTrue(result.equals("Example Text"));
    }

}
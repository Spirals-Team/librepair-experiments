package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LowerTransformTest {
    private Transform transform;
    private LowerTransform lowerTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.lowerTransform = new LowerTransform(transform);
    }

    @Test
    public void testLowerFromUpperShouldSucceed() {
        String result = lowerTransform.apply("EXAMPLE TEXT");
        assertTrue(result.equals("example text"));
    }

    @Test
    public void testLowerFromVariousShouldSucceed() {
        String result = lowerTransform.apply("eXampLe tExT");
        assertTrue(result.equals("example text"));
    }

    @Test
    public void testLowerFromLowerShouldSucceed() {
        String result = lowerTransform.apply("example text");
        assertTrue(result.equals("example text"));
    }

}
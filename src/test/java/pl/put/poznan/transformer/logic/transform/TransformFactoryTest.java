package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransformFactoryTest {
    private TransformFactory factory;

    @Before
    public void setUp() {
        this.factory = new TransformFactory();
    }

    @Test
    public void testKnownTransformationNameReturnValidTransformClass() {
        Transform result = factory.getTransform("upper", new FinalTransform());
        assertTrue(result.getClass() == UpperTransform.class);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenInvalidTransformationNameGiven() {
        factory.getTransform("invalid", new FinalTransform());
    }

}
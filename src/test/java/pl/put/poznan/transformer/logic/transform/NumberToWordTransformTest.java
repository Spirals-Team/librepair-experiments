package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NumberToWordTransformTest {
    private Transform transform;
    private NumberToWordTransform numberToWordTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.numberToWordTransform = new NumberToWordTransform(transform);
    }

    @Test
    public void testZeroShouldSucceed(){
        String result = numberToWordTransform.apply("0");
        assertTrue(result.equals("zero"));
    }

    @Test
    public void testEmpytStringShouldSucceed(){
        String result = numberToWordTransform.apply("");
        assertTrue(result.equals(""));
    }

    @Test
    public void testNumberAtTheEndOfSentenceShouldSucceed(){
        String result = numberToWordTransform.apply("0.01.");
        assertTrue(result.equals("jedna setna."));
    }

    @Test
    public void testMinusZeroShouldSucceed(){
        String result = numberToWordTransform.apply("-0");
        assertTrue(result.equals("zero"));
    }

    @Test
    public void testMinusShouldSucceed(){
        String result = numberToWordTransform.apply("-1.1");
        assertTrue(result.equals("minus jeden i jedna dziesiąta"));
    }

    @Test
    public void testMaxDecimalShouldSucceed(){
        String result = numberToWordTransform.apply("0.99");
        assertTrue(result.equals("dziewięćdziesiąt dziewięć setnych"));
    }

    @Test
    public void testTooManyDecimalPlacesShouldSucceed(){
        String result = numberToWordTransform.apply("0.001");
        assertTrue(result.equals("0.001"));
    }

    @Test
    public void testMaxNumberShouldSucceed(){
        String result = numberToWordTransform.apply(String.valueOf(Long.MAX_VALUE)+".99");
        assertTrue(result.equals("dziewięć trylionów dwieście dwadzieścia trzy biliardy trzysta siedemdziesiąt dwa biliony trzydzieści sześć miliardów osiemset pięćdziesiąt cztery miliony siedemset siedemdziesiąt pięć tysięcy osiemset siedem i dziewięćdziesiąt dziewięć setnych"));
    }

}
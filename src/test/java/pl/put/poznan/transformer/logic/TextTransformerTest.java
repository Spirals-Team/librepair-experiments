package pl.put.poznan.transformer.logic;

import org.junit.Test;
import pl.put.poznan.transformer.logic.transform.UpperTransform;

import static org.junit.Assert.assertEquals;

public class TextTransformerTest {

    @Test
    public void testSingleTransformationUpperShouldSucceed() {
        String[] transform = new String[1];
        transform[0] = "upper";
        String text = "This is sample Text!?";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("THIS IS SAMPLE TEXT!?", result);
    }

    @Test
    public void testSingleTransformationLowerShouldSucceed() {
        String[] transform = new String[1];
        transform[0] = "lower";
        String text = "This is sample Text!?";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("this is sample text!?", result);
    }

    @Test
    public void testSingleTransformationCapitalizeShouldSucceed() {
        String[] transform = new String[1];
        transform[0] = "capitalize";
        String text = "This is samPle Text!?";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("This Is Sample Text!?", result);
    }

    @Test
    public void testMultipleTransformationsUpperCapitalizeShouldSucceed() {
        String[] transform = new String[2];
        transform[0] = "upper";
        transform[1] = "capitalize";
        String text = "This is sample Text";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("This Is Sample Text", result);
    }

    @Test
    public void testMultipleTransformationsCapitalizeLowerShouldSucceed() {
        String[] transform = new String[2];
        transform[0] = "capitalize";
        transform[1] = "lower";
        String text = "This is sample Text";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("this is sample text", result);
    }

    @Test
    public void testMultipleTransformationsInverseUpperShouldSucceed() {
        String[] transform = new String[2];
        transform[0] = "upper";
        transform[1] = "inverse";
        String text = "This is sample Text";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("TXET ELpMAS SI SIHT", result);
    }

    @Test
    public void testMultipleTransformationsInverseLowerShouldSucceed() {
        String[] transform = new String[2];
        transform[0] = "lower";
        transform[1] = "inverse";
        String text = "This is sample Text";
        TextTransformer transformer = new TextTransformer(transform);
        String result = transformer.transform(text);
        assertEquals("txet elpmas si siht", result);
    }

}
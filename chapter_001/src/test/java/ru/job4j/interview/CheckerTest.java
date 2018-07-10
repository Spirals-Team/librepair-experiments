package ru.job4j.interview;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class CheckerTest {

    @Test
    public void checkStringsWhitTheSameSymbols() {
        String first = "abba";
        String second = "aabb";
        assertTrue(new Checker().checkSymbolOccurrences(first, second));
    }

    @Test
    public void checkEqualStrings() {
        String first = "abba";
        String second = "abba";
        assertTrue(new Checker().checkSymbolOccurrences(first, second));
    }

    @Test
    public void checkStringsWhitDifferentLength() {
        String first = "abba";
        String second = "abb";
        assertFalse(new Checker().checkSymbolOccurrences(first, second));
    }

    @Test
    public void checkEmptyStrings() {
        String first = "";
        String second = "";
        assertTrue(new Checker().checkSymbolOccurrences(first, second));
    }

    @Test
    public void checkStringAgainstEmptyString() {
        String first = "abba";
        String second = "";
        assertFalse(new Checker().checkSymbolOccurrences(first, second));
    }

    @Test
    public void checkIfOneOfStringIsNull() {
        String first = "abba";
        String second = null;
        assertFalse(new Checker().checkSymbolOccurrences(first, second));
    }
}
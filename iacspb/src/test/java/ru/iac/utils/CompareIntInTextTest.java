package ru.iac.utils;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CompareIntInTextTest {

    @Test
    public void whenTestCompareTwoWords() {
        assertThat(CompareIntInText.compareString("1", "Test"), is(-1));
        assertThat(CompareIntInText.compareString("1", "002"), is(-1));
        assertThat(CompareIntInText.compareString("1", "0"), is(1));
        assertThat(CompareIntInText.compareString("01", "0000001"), is(0));
        assertThat(CompareIntInText.compareString("Test", "tEsT"), is(0));

    }

    @Test
    public void whenTestCompareTwoChars() {
        assertThat(CompareIntInText.compareArraysChars("1".toCharArray(), "001".toCharArray() ), is(0));
        assertThat(CompareIntInText.compareArraysChars("1".toCharArray(), "002".toCharArray() ), is(-1));
        assertThat(CompareIntInText.compareArraysChars("1".toCharArray(), "0301".toCharArray() ), is(-1));
        assertThat(CompareIntInText.compareArraysChars("1".toCharArray(), "00000".toCharArray() ), is(1));
        assertThat(CompareIntInText.compareArraysChars("T1".toCharArray(), "T001".toCharArray() ), is(0));
        assertThat(CompareIntInText.compareArraysChars("M21".toCharArray(), "M001".toCharArray() ), is(1));
    }

    @Test
    public void whenTestMetodGetSymbols() {
        String text = "0000001";
        List<Object> list = new ArrayList<>();
        list.add(1);
        assertThat(CompareIntInText.getSymbols(text.toCharArray()), is(list));
        text = "0101";
        list.clear();
        list.add(101);
        assertThat(CompareIntInText.getSymbols(text.toCharArray()), is(list));
    }

    @Test
    public void whenTestIsNum() {
        String text = "0-9oO";
        char[] chars = text.toCharArray();
        assertTrue(CompareIntInText.isNum(chars[0]));
        assertFalse(CompareIntInText.isNum(chars[1]));
        assertTrue(CompareIntInText.isNum(chars[2]));
        assertFalse(CompareIntInText.isNum(chars[3]));
        assertFalse(CompareIntInText.isNum(chars[4]));
    }
}
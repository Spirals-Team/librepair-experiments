package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ContainsTest {

    /**
     * Тест входа подстроки в строку которая есть.
     */
    @Test
    public void whereSubStringInStringTrue() {
        Contains contains = new Contains();
        String testSt = "Hello";
        String testSubSt = "ell";
        boolean testFlag = contains.contains(testSt, testSubSt);
        boolean expectFlag = true;
        assertThat(testFlag, is(expectFlag));
    }

    /**
     * Тест входа подстроки в строку которая есть, но не сразу .
     */
    @Test
    public void whereSubStringInStringTrueDificul() {
        Contains contains = new Contains();
        String testSt = "abcabeab";
        String testSubSt = "be";
        boolean testFlag = contains.contains(testSt, testSubSt);
        boolean expectFlag = true;
        assertThat(testFlag, is(expectFlag));
    }

    /**
     * Тест входа подстроки в строку которой нет.
     */
    @Test
    public void whereSubStringInStringFalse() {
        Contains contains = new Contains();
        String testSt = "Hello";
        String testSubSt = "la";
        boolean testFlag = contains.contains(testSt, testSubSt);
        boolean expectFlag = false;
        assertThat(testFlag, is(expectFlag));
    }
}
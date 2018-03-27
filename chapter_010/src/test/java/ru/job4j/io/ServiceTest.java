package ru.job4j.io;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class ServiceTest {
    private String file = "number.txt";
    private String input = "input.txt";
    private InputStream is;
    private Service service;
    String[]ab;
    private InputStream is2;

    @Before
    public void init() throws FileNotFoundException {
        service = new Service();
        is = getClass().getClassLoader().getResourceAsStream(file);
        ab = new String[]{"уппс", "чух", "пух"};
        is2 = getClass().getClassLoader().getResourceAsStream(input);
    }

    @Test
    public void isEvenNumber() {
        boolean result =  service.isNumber(is);
        assertThat(result, is(true));
    }

    @Test
    public void whenTextContainsAnyWordsThenNoWords() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        service.dropAbuses(is2, bos, ab);
        String s = bos.toString();
        boolean contains = false;
        for (String abuse : ab) {
            if (s.contains(abuse)) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);
    }
    @Test
    public void whenTextContainsAnyWordsThenNoWords2() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        service.dropAbuses2(is2, bos, ab);
        String s = bos.toString();
        System.out.println(s);
        boolean contains = false;
        for (String abuse : ab) {
            if (s.contains(abuse)) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);

    }
}
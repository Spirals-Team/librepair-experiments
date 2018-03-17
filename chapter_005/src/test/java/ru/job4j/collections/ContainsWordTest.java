package ru.job4j.collections;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Hincu Andrei (andreih1981@gmail.com)on 21.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ContainsWordTest {
    /**
     * Объект.
     */
    private ContainsWord con;

    /**
     * Инициализация.
     */
    @Before
    public void start() {
         con = new ContainsWord();
    }

    /**
     * Слово состоит из букв второго слова.
     */
    @Test
    public void whenFirstWordContainsSecondCharsOfWord() {
        boolean contains = con.contains("кавалерист", "акварелист");
        assertThat(contains, is(true));
    }

    /**
     * Тест второго способа.
     */
    @Test
    public void whenUsingSort() {
        boolean cont = con.contains2("mama", "amam");
        assertThat(cont, is(true));
    }
}
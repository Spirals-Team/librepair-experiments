package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;



/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 02.10.17
 * @ version: java_kurs_standart+
 * 5.1.4. Создать convert(Iterator<Iterator>).
 * Тестовый класс.
 */
public class ConvertIteratorTest {
    /**
     * Проверим итератор итераторов.
     *
     */
    @Test
    public void whenItHasTwoInnerIt() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();
        Iterator<Integer> convert = new ConvertIterator().convert(it);
        convert.next();
        int result = convert.next();
        Assert.assertEquals(result, 2);
    }
    /**
     * Проверим ненулевой итератор.
     *
     */
    @Test
    public void whenNullIteratorThen() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator()
        ).iterator();
        Iterator<Integer> convert = new ConvertIterator().convert(it);
        Assert.assertTrue(convert.hasNext());
    }
}
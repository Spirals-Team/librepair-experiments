package ru.job4j.collections.set;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Тест.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 13.10.17;
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSetTest {
    /**
     * Тест на время исполнения операции по добавлению новых элементов..
     */
    @Test
    public void whenUsedSetWithArrayAndWithHash() {
        SimpleHashSet<Integer> set = new SimpleHashSet(1000000);
        long t = -System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            set.put(i);
        }
        long timeWithHash = System.currentTimeMillis() + t;


        SimpleSet<Integer> set1 = new SimpleSet<>(10);
        long tS = -System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            set1.add(i);
        }
        long tSet = System.currentTimeMillis() + tS;
       assertThat(timeWithHash < tSet, is(true));
    }

}
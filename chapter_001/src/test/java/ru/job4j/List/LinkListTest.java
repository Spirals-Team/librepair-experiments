package ru.job4j.List;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Тесты для связанного списка с типом Integer
 */
public class LinkListTest {
    LinkList<Integer> simple = new LinkList<>();

    @Before
    public void initValues() {
        simple.add(1);
        simple.add(2);
        simple.add(3);
        simple.add(4);
    }
    @Test
    public void whenRemoveCellThenSizeLess() {
        assertThat(simple.size(), is(4));
        assertThat(simple.remove(3), is(true));
        assertThat(simple.size(), is(3));
    }

    @Test
     public void whenAddCellThenSizeMore() {
        simple.add(5);
        assertThat(simple.size(), is(5));
    }

    @Test
    public void whenAddCellThenGetThisCellBack() {
        simple.add(5);
        assertThat(simple.get(4), is(5));
    }

    @Test
    public void testIterator() {
        Iterator<Integer> simpleiter = simple.iterator();
        assertThat(simpleiter.hasNext(), is(true));
        assertThat(simpleiter.next(),is(1));
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(true));
        simpleiter.next();
        simpleiter.next();
        assertThat(simpleiter.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    /**
     *Тест ошибки итератора при изменении списка после создания оператора
     */
    public void getIteratorExceptionTest() {
        DynArray<Integer> simple = new DynArray<>();
        simple.add(1);
        simple.add(2);
        Iterator<Integer> simpleiter = simple.iterator();
        simple.add(3);
        simple.add(4);
        simpleiter.hasNext();
    }

}
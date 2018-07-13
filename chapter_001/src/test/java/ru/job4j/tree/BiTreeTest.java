package ru.job4j.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тесты бинарного дерева поиска
 */
public class BiTreeTest {
    BiTree<Integer> bitree = new BiTree<>(30);

    @Before
    public void initValues() {
        bitree.add(10);
        bitree.add(50);
        bitree.add(41);
        bitree.add(5);
        bitree.add(29);
        bitree.add(60);
    }

    @Test
    /**
     * Тест метода add
     * Проверяем есть ли значение в дереве.. потом добавляем это значение
     * И еще раз проверяем его наличие в дереве
     */
    public void testAdd() {
        assertThat(bitree.hasThisValue(100), is(false));
        bitree.add(100);
        assertThat(bitree.hasThisValue(100), is(true));
    }

    @Test
    /**
     * Тест итератора
     * Проверяем наличие следующего.. Идем в конец структуры.. Еще раз проверяем наличе следующего..
     */
    public void testIterator() {
        Iterator<Integer> iter = bitree.iterator();
        assertThat(iter.hasNext(), is(true));
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        assertThat(iter.hasNext(), is(false));
    }
}
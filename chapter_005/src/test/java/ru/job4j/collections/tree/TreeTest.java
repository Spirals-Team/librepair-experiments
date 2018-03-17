package ru.job4j.collections.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тесты.
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.10.17;
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {
    /**
     * Дерево.
     */
    private Tree<String> tree;

    /**
     * Инициализация.
     */
    @Before
    public void start() {
        tree = new Tree<>();
        boolean a = tree.add("1", "2");
        boolean b = tree.add("1", "3");
        boolean c = tree.add("3", "4");
    }

    /**
     * Метод проверяет добавление элемента.
     * попытку добавить существующего ребенка
     * попытку добавить несуществующего родителя.
     */
    @Test
    public void whenWasAddedExistingChild() {
        boolean j = tree.add("4", "5");
        assertThat(j, is(true));
        boolean f = tree.add("1", "5");
        assertThat(f, is(false));
        boolean g = tree.add("aaa", "15");
        assertThat(g, is(false));
    }

    /**
     *Метод проверяет работу итератора.
     */
    @Test
    public void iterator() {
        Iterator<String> it = tree.iterator();
        String one = it.next();
        assertThat(one, is("1"));
        it.next();
        it.next();
        String four = it.next();
        assertThat(four, is("4"));
    }

    /**
     * Тест является ли дерево бинарным.
     */
    @Test
    public void whenKnotHasThreeChildIsNotBinaryTree() {
        assertThat(tree.isBinary(), is(true));
        tree.add("3", "6");
        tree.add("3", "8");
        assertThat(tree.isBinary(), is(false));

    }
}
package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 26.11.17
 * @ version: java_kurs_standart
 */
public class TreeTest {
    /**
     * Для обработки ошибок.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();
    /**
     * Добавим корневой элемент и дочерний.
     * @throws Exception исключение.
     */
    @Test
    public void tryAddTwoElements() throws Exception {
        Tree<String> tree = new Tree<>();
        tree.add("0", "1");
        tree.add("1", "11");
        Iterator iterator = tree.iterator();
        Assert.assertEquals(iterator.next(), "1");
    }
    /**
     * Пробуем добавить два узла с разным родителем.
     * @throws Exception исключение.
     */
    @Test
    public void whenAddTwoDifferentElementsThenReturnOne() throws Exception {
        testException.expect(NullPointerException.class);
        Tree<String> tree = new Tree<>();
        tree.add("0", "1");
        tree.add("2", "21");
    }
    /**
     * Пробуем добавить дубликат.
     * @throws Exception исключение.
     */
    @Test
    public void whenAddDublicateThenReturnOne() throws Exception {
        testException.expect(IndexOutOfBoundsException.class);
        Tree<String> tree = new Tree<>();
        tree.add("0", "1");
        tree.add("0", "2");
        tree.add("0", "2");

        Iterator iterator = tree.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    /**
     * Проверим бинарное дерево.
     * @throws Exception исключение.
     */
    @Test
    public void whenTreeBinaryThenReturnTrue() throws Exception {
        Tree<String> tree = new Tree<>();
        tree.add("0", "1");
        tree.add("0", "2");

        Assert.assertTrue(tree.isBinary(tree.getRoot()));
    }
    /**
     * Проверим небинарное дерево.
     * @throws Exception исключение.
     */
    @Test
    public void whenTreeNotBinaryThenReturnFalse() throws Exception {
        Tree<Integer> tree = new Tree<>();
        tree.add(50, 70);
        tree.add(50, 80);
        tree.add(70, 90);
        tree.add(70, 100);
        tree.add(90, 110);
        tree.add(90, 120);
        tree.add(90, 240);
        Assert.assertThat(tree.isBinary(tree.getRoot()), is(false));

    }
}
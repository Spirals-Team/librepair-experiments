package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 28.11.17
 * @ version: java_kurs_standart
 */
public class BinarySearchTreeTest {
    /**
     * Для обработки ошибок.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /**
     * Добавим элементы и получим один.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddNodesThenAdded() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("55");
        tree.add("33");
        tree.add("77");
        tree.add("22");
        tree.add("44");
        tree.add("66");
        tree.add("88");

        Assert.assertEquals(tree.getNode("77").getValue(), "77");
    }

    /**
     * Добавим элементы и получим несуществующий.
     *
     * @throws Exception исключение.
     */
    @Test
    public void addNodesAndTryGetNoElement() throws Exception {
        testException.expect(NoSuchElementException.class);
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("55");
        tree.add("33");
        tree.add("77");
        tree.add("22");
        tree.add("44");
        tree.add("66");
        tree.add("88");

        tree.getNode("99");
    }

    /**
     * Добавим элементы и получим минимум.
     * минимум - крайний левй элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetMinimum() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("55");
        tree.add("33");
        tree.add("77");
        tree.add("22");
        tree.add("44");
        tree.add("66");
        tree.add("88");

        Assert.assertEquals(tree.minNode(tree.getRoot()), "22");
    }

    /**
     * Добавим элементы и получим максимум.
     * Максимум - крайний правй элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetMaximum() throws Exception {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("55");
        tree.add("33");
        tree.add("77");
        tree.add("22");
        tree.add("44");
        tree.add("66");
        tree.add("88");

        Assert.assertEquals(tree.maxNode(tree.getRoot()), "88");
    }
}
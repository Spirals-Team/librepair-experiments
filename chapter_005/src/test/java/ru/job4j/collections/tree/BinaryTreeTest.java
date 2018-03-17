package ru.job4j.collections.tree;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Тест.
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.10.17;
 * @version $Id$
 * @since 0.1
 */
public class BinaryTreeTest {
    /**
     * Дерево.
     */
   private BinaryTree<Integer> binaryTree;

    /**
     * Инициализация.
     */
    @Before
    public void start() {
        binaryTree = new BinaryTree<>();
        binaryTree.add(10);
        binaryTree.add(5);
        binaryTree.add(12);
        binaryTree.add(12);

    }

    /**
     * Проверка размера при добавлении одного неуникального элемента.
     */
    @Test
    public void whenWasAddedFourElements() {
        assertThat(binaryTree.getSize(), is(3));
    }
}
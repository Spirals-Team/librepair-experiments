package ru.job4j.collections.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Node Задача : написать метод который меняет значения в дереве с левого на правый.
 * @author Hincu Andrei (andreih1981@gmail.com)on 28.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Node {
    /**
     * левый элемент.
     */
    private Node left;
    /**
     * правый элемент.
     */
    private Node right;
    /**
     * Значение элемента.
     */
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    /**
     * Метод меняет местами павый и левый элемент дерева.
     */
    public void revers() {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left != null && node.right != null) {
                Node temp = node.left;
                node.left = node.right;
                node.right = temp;
                queue.add(node.right);
                queue.add(node.left);
            } else if (node.left != null) {
                node.right = node.left;
                node.left = null;
                queue.add(node.right);
            } else if (node.right != null) {
                node.left = node.right;
                node.right = null;
                queue.add(node.left);
            }
        }
    }
}

package ru.job4j.tree;

import java.util.*;

//метод add - Должен находить элемент parent в дереве по условию compare(node, parent) == 0
// и добавлять в него дочерний элемент.
//        node.children.add(child);
//        В дереве не могут быть дубликатов.
//        Итератор должен собрать все элементы в List и возвращать данные из скопированной коллекции.

//WORK ON BURS
//        1) использование аргумента-флага (itIsBFS) в методе findNodeBFSorDFS. Считается плохим стилем программирования.
//        Это свидетельствует о том, что метод выполняет более одной операции.
//
//        2) в случае если в конструктор не передается компаратор, вы сравниваете объекты через ссылочное равенство
//        if (current.getValue() == value). Это не верно. Вы же параметризуете дерево как E extends Comparable<E>,
//        поэтому для корректной работы необходимо вызывать метод compareTo

//!!! тут надо использовать для поиска обход в глубину. а для итератора обход в ширину.
class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    private Comparator<? super E> comparator = null;

    public Tree() {
        comparator = null;
    }

    public Tree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public static class Node<E> {
        List<Node<E>> children;
        E value;

        public List<Node<E>> getChildren() {
            return children;
        }

        public E getValue() {
            return value;
        }

        public Node(List<Node<E>> childen, E value) {
            this.children = childen;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node<?> node = (Node<?>) o;

            if (children != null ? !children.equals(node.children) : node.children != null) {
                return false;
            }

            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            int result = children != null ? children.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    private Node<E> root = null;

    public Node<E> getRoot() {
        return root;
    }

    //метод add - Должен находить элемент parent в дереве по условию compare(node, parent) == 0
    // и добавлять в него дочерний элемент. node.children.add(child);
    @Override
    public boolean add(E parent, E child) {

        boolean returnValue = false;

        if (root == null) {
            addRoot(parent, child);
            return true;
        }

        if (findNodeBFSorDFS(root, child, false) != null) {
            return false;
        }

        Node<E> currNodeWithParent = findNodeBFSorDFS(root, parent, false); // findNodeDFS(root, parent);

        if (parent != null) {

            List<Node<E>> listCurrNodeChildren = currNodeWithParent.children;

            List<Node<E>> childChildList = new ArrayList<>();

            Node<E> childNode = new Node<E>(childChildList, child);

            listCurrNodeChildren.add(childNode);

            returnValue = true;
        }

        return returnValue;
    }

    private void addRoot(E parent, E child) {

        List<Node> childrenList = new ArrayList<>();

        List<Node> childrenChildrenList = new ArrayList<>();

        Node<E> childNode = new Node(childrenChildrenList, child);

        childrenList.add(childNode);

        Node rootNode = new Node(childrenList, parent);

        root = rootNode;
    }

    //    Поиск в глубину (англ. Depth-first search, DFS)
//    Поиск в ширину (англ. breadth-first search, BFS)
//        1) использование аргумента-флага (itIsBFS) в методе findNodeBFSorDFS. Считается плохим стилем программирования.
//        Это свидетельствует о том, что метод выполняет более одной операции.
    public Node<E> findNodeBFSorDFS(Node<E> root, E value, boolean itIsBFS) {

        LinkedList<Node<E>> nodes = new LinkedList<Node<E>>() { {
            add(root);
        } };

        while (!nodes.isEmpty()) {

            Node current = null;

//            removeLast() - это стэк; а removeFirst - очередь
            if (itIsBFS) { //
                current = nodes.removeFirst(); //очередь
            } else { // findNodeDFS
                current = nodes.removeLast(); //стэк
            }

            Comparator<? super E> cpr = comparator;
            if (comparator == null) {
//                if (current.getValue() == value) {
                //        2) в случае если в конструктор не передается компаратор, вы сравниваете объекты через ссылочное равенство
//        if (current.getValue() == value). Это не верно. Вы же параметризуете дерево как E extends Comparable<E>,
//        поэтому для корректной работы необходимо вызывать метод compareTo
                int cmp = ((E) current.getValue()).compareTo(value);
                if (cmp == 0) {
                    return current;
                }
            } else {
                int cmp = cpr.compare((E) current.getValue(), value);

                if (cmp == 0) {
                    return current;
                }
            }
            nodes.addAll(current.getChildren());
        }
        return null;
    }

    //    Итератор должен собрать все элементы в List и возвращать данные из скопированной коллекции.
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {

        LinkedList<Node<E>> nodes = new LinkedList<Node<E>>() { {
            add(root);
        } };

        @Override
        public boolean hasNext() {

            return !nodes.isEmpty();
        }

        @Override
        public E next() {

            if (!hasNext()) {

                throw new NoSuchElementException();

            } else  {

                Node current = nodes.removeFirst(); //nodes.removeLast() - это стэк; а removeFirst - очередь

                nodes.addAll(current.getChildren());

                return (E) current.getValue();
            }
        }
    }

    //    Метод должен проверять количество дочерних элементов в дереве. Если их <= 2 - то дерево бинарное.
    public boolean isBinary() {

        boolean treeIsBinary = true;

        LinkedList<Node<E>> nodes = new LinkedList<Node<E>>() { {
            add(root);
        } };

        while (!nodes.isEmpty()) {

            Node current = null;

            current = nodes.removeFirst(); //очередь

            List<Node<E>> currentChildren = current.getChildren();

            if (currentChildren.size() > 2) {
                treeIsBinary = false;
                return treeIsBinary;
            }

            nodes.addAll(currentChildren);
        }
        return treeIsBinary;
    }

}
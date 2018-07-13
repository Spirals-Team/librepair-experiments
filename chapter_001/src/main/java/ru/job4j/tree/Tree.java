package ru.job4j.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс моделирующий структуру дерева
 * @param <E> класс, объекты которого можно сравнивать друг с другом
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E>  {
    // корень дерева
    private Node<E> root = null;
    // счетчик модификаций структуры
    private int modCount = 0;

    /**
     * Конструктор, создающий корень дерева
     * @param root значение корня
     */
    public Tree( E root) {
        this.root = new Node<>(root);
    }

    /**
     * Класс, описывающий узел дерева и его функционал
     * @param <E> класс, объекты которого можно сравнивать друг с другом
     */
    public static class Node<E extends Comparable<E>> {
        private final List<Node<E>> children = new ArrayList<>();
        private final E value;

        /**
         * Конструктор, инициализирущий узел значением
         * @param value значение
         */
        public Node(final E value) {
            this.value = value;
        }

        /**
         * Метод возвращаюший значение значения узла
         * @return значение
         */
        public E getValue() {
            return value;
        }

        /**
         * Метод добавляет дите узлу
         * @param child дите
         */
        public void add(Node<E> child) {
            this.children.add(child);
        }

        /**
         * Метод возвращает список детей узла
         * @return список
         */
        public List<Node<E>> leaves() {
            return this.children;
        }

        /**
         * Метод сравниваеи текущее значение узла с заданным
         * @param that заданное значение
         * @return равен или нет
         */
        public boolean eqValue(E that) {
            return this.value.compareTo(that) == 0;

        }
    }

    @Override
    /**
     * Метод добавляет заданному узлу дочерний узел
     * @param parent родительский узел
     * @param child дочерний узел
     * @return успех добавления
     */
    public boolean add(E parent, E child) {
        boolean result = false;
        if (findBy(parent).isPresent() && ! findBy(child).isPresent()) {
            findBy(parent).get().add(new Node(child));
            modCount++;
            result = true;
        }
        return result;
    }

    @Override
    /**
     * Метод ищет в дереве узел с нужным значением
     * @param value значение
     * @return искомый узел в оболочке
     */
    public Optional<Node<E>> findBy(E value) {
        return convertTreeToCollect().stream().filter((s) -> s.eqValue(value)).findFirst();
    }

    /**
     * Метод конвертирует дерево в коллекцию, для удобства дальнейшей обработки
     * @return полученная коллекция
     */
    private Collection<Node<E>> convertTreeToCollect() {
        Collection<Node<E>> result = new ArrayList<>();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            result.add(el);
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return result;
    }

    /**
     * Метод проверяет количество потомков у узла и если их меньше 3х то возвращает успех
     * @return количество потомков меньше 3х
     */
    public boolean isBinary() {
        return convertTreeToCollect().stream().noneMatch((s) -> s.leaves().size() > 2);
    }


    @Override
    /**
     * Переопределяем итератор
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int position = 0;
            int expectedModCount = modCount;
            // список значений элементов в дереве
            List<E> data = convertTreeToCollect().stream().map((s) -> s.value).collect(Collectors.toList());

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return position < data.size();
            }

            @Override
            public E next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                return data.get(position++);
            }
        };
    }
}

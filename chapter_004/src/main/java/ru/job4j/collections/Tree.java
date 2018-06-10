package ru.job4j.collections;

import java.util.*;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tree<T extends Comparable<T>> implements SimpleTree<T> {

    private Leaf<T> root;
    private int size;

    public Tree(T root) {
        this.root = new Leaf(root);
        this.size = 1;
    }

    /**
     * Метод добавляет элемент в дерево. Сначала проверяем есть ли добавляемый элемент уже в дереве, если нет,
     * то создаем новый узел, содержащий элемент и к дочерним элементам передаваемого корня прибавляем этот узел.
     * @param parent, child - корень, в который добавляем child.
     * @return true, если элемента не было еще в дереве.
     */
    @Override
    public boolean add(T parent, T child) {
        Optional<Leaf<T>> newRoot = findBy(parent);
        Optional<Leaf<T>> newChild = findBy(child);
        if (newChild.isPresent() && newChild.get().eqValue(child)) {
            return false;
        }
            Leaf<T> childLeaf = new Leaf<>(child);
            newRoot.get().add(childLeaf);
            size++;
            return true;
    }

    /**
     * Метод рекурсивно проверяется есть ли у узла более двух потомков, если нет, то вызывает сам себя
     * у его потомков. если более двух потомков найдено на какой-то итерации, то возвращает false.
     */
    private boolean resultIsBinary = true;
    public boolean isBinary() {
        if (root.leaves().size() <= 2) {
            for (Leaf<T> child : root.leaves()) {
                if (child.leaves().size() > 2) {
                    resultIsBinary = false;
                }
                root = child;
                isBinary();
            }
        } else {
            return false;
        }
        return resultIsBinary;
    }

    /**
     * Метод ищет по дереву узел со значением value. Создаем очередь, куда добавляем корни и их дочерние элементы.
     * Проверяем каждый элемент из очереди на равенство value
     * @param value - искомый элемент.
     * @return контейнер с узлом элемента.
     */
    @Override
    public Optional<Leaf<T>> findBy(T value) {
        Optional<Leaf<T>> rsl = Optional.empty();
        Queue<Leaf<T>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Leaf<T> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Leaf<T> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    private Queue<Leaf<T>> data = new LinkedList<>();
    /**
     * Метод в очередь добавляет корень дерева, а затем его потомков и рекурсивно вызывает самого
     * себя для каждого потомка, тем самый записывая в очередь все дерево.
     * @param root - корень дерева.
     */
    private void initialize(Leaf<T> root) {
        data.offer(root);
        for (Leaf<T> child : root.leaves()) {
            initialize(child);
        }
    }

    @Override
    public Iterator<T> iterator() {
        initialize(root);
        return new Iterator<T>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count <  size;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T result = data.peek().getValue();
                    data.poll();
                    count++;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}

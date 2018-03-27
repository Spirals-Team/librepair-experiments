package ru.job4j.collections.simpletree;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 11.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Tree<T extends Comparable<T>> implements SimpleTree<T> {
    private static final Logger LOG = LogManager.getLogger(Tree.class);
    private Node<T> root;

    public Tree(T i) {
        this.root = new Node<>(i);
    }

    @Override
    public boolean add(T parent, T child) {
        boolean found = false;
        if (!findBy(child).isPresent()) {
            if (!findBy(parent).isPresent()) {
                this.root.add(new Node<>(parent));
            }
          Node<T> p =  findBy(parent).get();
          p.add(new Node<>(child));
            found = true;
        }
        return found;
    }

    @Override
    public Optional<Node<T>> findBy(T value) {
        Optional<Node<T>> rsl = Optional.empty();
        Queue<Node<T>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<T> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<T> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}

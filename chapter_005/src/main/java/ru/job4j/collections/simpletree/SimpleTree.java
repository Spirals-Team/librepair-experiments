package ru.job4j.collections.simpletree;

import java.util.Optional;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 11.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    boolean add(E p, E c);
    Optional<Node<E>> findBy(E value);

}

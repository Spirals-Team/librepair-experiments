package ru.job4j.dao;

import ru.job4j.model.Persistent;

import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 18.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public interface Dao<E extends Persistent, K> {

    List<E> getAll();

    E getEntityById(K id);

    boolean delete(K id);

    void delete(E entity);

    K save(E entity);

    void update(E entity);

}

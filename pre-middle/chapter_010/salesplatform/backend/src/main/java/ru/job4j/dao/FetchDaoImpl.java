package ru.job4j.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class FetchDaoImpl<T> implements FetchDao<T> {

    private final Class<T> persistentClass =
        (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public T findOne(final Long key) {
        return this.fetchTx(session -> session.find(persistentClass, key));
    }

    @Override
    public List<T> findAll() {
        return this.fetchTx(session -> session.createQuery(
            String.format("from %s", persistentClass.getSimpleName())).list());
    }
}

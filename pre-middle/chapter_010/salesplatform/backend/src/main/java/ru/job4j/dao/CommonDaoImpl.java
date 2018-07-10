package ru.job4j.dao;

import java.util.List;

public abstract class CommonDaoImpl<T> extends FetchDaoImpl<T> implements CrudDao<T> {

    @Override
    public Long create(final T entity) {
        return (Long) this.fetchTx(session -> session.save(entity));
    }

    @Override
    public void update(final T entity) {
        this.modifyTx(session -> session.update(entity));
    }

    public void updateAll(final List<T> entities) {
        entities.forEach(this::update);
    }

    @Override
    public void delete(final T entity) {
        this.modifyTx(session -> session.delete(entity));
    }

    @Override
    public void deleteById(final Long key) {
        delete(findOne(key));
    }
}

package ru.job4j.dao;

public interface CrudDao<T> extends AbstractDao {

    Long create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(Long key);
}

package ru.job4j.dao;

import java.util.List;

public interface FetchDao<T> extends AbstractDao {

    T findOne(Long key);

    List<T> findAll();
}

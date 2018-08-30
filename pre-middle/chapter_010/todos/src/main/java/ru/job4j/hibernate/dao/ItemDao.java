package ru.job4j.hibernate.dao;

import ru.job4j.hibernate.models.Item;

import java.util.List;

/**
 * DAO Interface for Item.
 */
public interface ItemDao {

    Item findOne(Long key);

    List<Item> findAll();

    Long create(Item entity);

    boolean update(Item entity);

    boolean delete(Item entity);

    boolean deleteById(Long key);
}

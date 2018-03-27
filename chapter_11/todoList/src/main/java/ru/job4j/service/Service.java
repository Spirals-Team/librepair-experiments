package ru.job4j.service;

import ru.job4j.model.Item;

import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 13.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public interface Service {
    void add(String description);

    List<Item> getItems(String status);

    void update(int id, boolean done);

    void close();
}

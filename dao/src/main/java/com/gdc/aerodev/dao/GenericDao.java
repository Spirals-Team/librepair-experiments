package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;

import javax.sql.DataSource;
import java.util.List;

/**
 * This is basic generic data access object interface. All addons can be added in child.
 *
 * @param <T> is the type of entity object, for example: {@code User}, {@code Cr}, {@code Project} and so on.
 * @param <V> is the type of ID {@code Long}, {@code Integer} or {@code Short}.
 *
 * @author Yusupov Danil
 */
public interface GenericDao<T, V> {

    /**
     * Gets {@code T} entity from connected database with inserted {@param id}
     * @param id identifier of target {@code T}
     * @return (0) found {@code T} with matched {@param id}
     *         (1) null if there is no such {@code T}
     */
    T getById(V id);

    /**
     * Gets {@code T} entity from connected database with inserted {@param name}
     * @param name name of target {@code T}
     * @return (0) found {@code T} with matched {@param name}
     *         (1) null if there is no such {@code T}
     */
    T getByName(String name);

    /**
     * Gets all existent {@code T} entities from database
     * @return {@code List} of found {@code T} entities, even empty {@code List}
     */
    List<T> getAll();

    /**
     * Inserts new {@code T} if {@param id} is {@code null} or updates it if {@param id} is {@code !null}
     * @param entity target {@code T} to insert or update in database
     * @return {@param id} of inserted or updated {@code T}
     * @throws DaoException if {@param name} or another params is already registered in database
     */
    V save(T entity);

    /**
     * Deletes {@code T} entity from connected database by inserted {@param id}
     * @param id identifier of target {@code T}
     * @return (0) {@code true} if deleting was performed or
     *         (1) {@code false} if nothing was deleted
     */
    boolean delete(V id);

}

package com.gdc.aerodev.dao;

import com.gdc.aerodev.dao.exception.DaoException;
import org.springframework.dao.DuplicateKeyException;

public abstract class AbstractDao<T, V> implements GenericDao<T, V> {

    /**
     * Method inherited from {@code GenericDao} & checks which method needs to be invoked. If {@code entityId} is
     * {@code null}, then {@code insert()} method will execute. If {@code entityId} isn't {@code null}, then
     * {@code update()} method will execute.
     *
     * @param entity {@code T} entity to save
     * @return id of saved or updated entity
     */
    @Override
    public V save(T entity) {
        if (isNew(entity)){
            try {
                return insert(entity);
            } catch (DuplicateKeyException e){
                throw new DaoException("Error inserting entity: ", e);
            }
        } else {
            return update(entity);
        }
    }

    /**
     * Inserts new entity with {@code entityId == null} into database.
     *
     * @param entity {@code T} entity to save
     * @return {@code V} id of inserted entity
     */
    protected abstract V insert(T entity);

    /**
     * Updates entity with {@code entityId != null} into database.
     *
     * @param entity {@code T} entity to update
     * @return {@code V} id of updated entity
     */
    protected abstract V update(T entity);

    /**
     * Checks nullable of entity's ID.
     * @param entity target to check
     * @return (1) {@code true} if ID of entity is {@code null}, (2) {@code false} if ID of entity is {@code not null}
     */
    protected abstract boolean isNew(T entity);

    public abstract int count();

}

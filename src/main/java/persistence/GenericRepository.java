package persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariano on 14/04/18.
 */
public interface GenericRepository<T> {


    void save(T entity);

    void delete(T entity);

    void update(T entity);

    T findById(Serializable id);

    List<T> findAll();

    int count();

}

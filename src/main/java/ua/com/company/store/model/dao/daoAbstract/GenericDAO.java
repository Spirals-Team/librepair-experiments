package ua.com.company.store.model.dao.daoAbstract;

import java.util.List;

/**
 * Created by Владислав on 22.11.2017.
 */
public interface GenericDAO<T> {
    int insert(T t);

    T update();

    T getById(int key);

    List<T> getAll();

    T getByParameter(String parameter);

}

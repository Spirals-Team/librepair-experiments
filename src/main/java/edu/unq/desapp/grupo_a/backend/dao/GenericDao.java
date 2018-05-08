package edu.unq.desapp.grupo_a.backend.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> {

	void save(T entity);

	void delete(T entity);

	void update(T entity);

	T findById(Serializable id);

	List<T> findAll();

	void deleteById(Serializable id);

	int count();

	List<T> findByExample(T exampleObject);
	
	Class<T> getDomainClass();

	
}

package edu.unq.desapp.grupo_a.backend.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.unq.desapp.grupo_a.backend.dao.GenericDao;

public abstract class GenericService<T> {

	public abstract GenericDao<T> getRepository();

	@Transactional
	public T save(T entity) {
		this.getRepository().save(entity);
		return entity;
	}

	@Transactional
	public T update(T entity) {
		this.getRepository().update(entity);
		return entity;
	}

	@Transactional
	public T find(Long id) {
		return this.getRepository().findById(id);
	}

	@Transactional
	public void delete(T entity) {
		this.getRepository().delete(entity);
	}

	@Transactional
	public List<T> findAll() {
		return this.getRepository().findAll();
	}

	@Transactional
	public void delete(Long id) {
		this.getRepository().deleteById(id);
	}

}

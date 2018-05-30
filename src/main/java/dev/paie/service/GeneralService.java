package dev.paie.service;

import java.util.List;

public interface GeneralService<T> {

	List<T> findAll(int id);

	void save(T object);

}

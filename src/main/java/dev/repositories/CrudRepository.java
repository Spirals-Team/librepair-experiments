package dev.repositories;

import java.util.List;

/**
 * Interface de base des services.
 *
 * @param <T>
 *            : nature de l'entité manipulée par le service.
 */
public interface CrudRepository<T> {
	List<T> findAll();

	void save(T entite);

	void update(T entiteAvecId);

	void delete(T entite);
}

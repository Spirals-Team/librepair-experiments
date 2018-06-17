package com.les.brouilles.planner.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.les.brouilles.planner.persistence.model.evenement.CommonEvenement;

@NoRepositoryBean
public interface EvenementBaseRepository<T extends CommonEvenement> extends CrudRepository<T, Long> {
	@Query("SELECT e FROM #{#entityName} e WHERE e.client.id = ?1 ORDER BY e.debut ASC")
	List<T> findEvenementsByClientId(Long clientId);

	// Tri des evenements par date debut asc
	List<T> findAllByOrderByDebutAsc();

	@Query("SELECT e FROM #{#entityName} e INNER JOIN e.client client ORDER BY e.debut ASC")
	List<T> findAllWithClientFetch();
}
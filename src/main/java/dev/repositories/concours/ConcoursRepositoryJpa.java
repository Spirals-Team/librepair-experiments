package dev.repositories.concours;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.entites.Concours;

public class ConcoursRepositoryJpa implements ConcoursRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Concours> findAll() {
		TypedQuery<Concours> query = em.createQuery("SELECT c FROM concours c", Concours.class);
		List<Concours> concours = query.getResultList();
		return concours;
	}

	@Transactional
	@Override
	public void save(Concours entite) {
		em.persist(entite);

	}

	@Transactional
	@Override
	public void update(Concours entiteAvecId) {
		if (entiteAvecId != null) {
			em.merge(entiteAvecId);
		}

	}

	@Transactional
	@Override
	public void delete(Concours entite) {
		Concours entite1 = em.find(Concours.class, entite.getId());
		if (entite1 != null) {
			em.remove(entite1);
		}

	}

	@Override
	public Optional<Concours> findById(Long id) {
		Concours c = em.find(Concours.class, id);
		return Optional.ofNullable(c);
	}

}

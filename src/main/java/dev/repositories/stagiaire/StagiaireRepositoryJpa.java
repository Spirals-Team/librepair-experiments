package dev.repositories.stagiaire;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.entites.Stagiaire;

public class StagiaireRepositoryJpa implements StagiaireRepository {

	// Récupération d'une instance de l'Entity Manager
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Stagiaire> findAll() {
		TypedQuery<Stagiaire> query = em.createQuery("SELECT s FROM Stagiaire s", Stagiaire.class);
		List<Stagiaire> stagiaires = query.getResultList();
		return stagiaires;
	}

	@Transactional
	@Override
	public void save(Stagiaire s) {
		em.persist(s);
	}

	@Transactional
	@Override
	public void update(Stagiaire stagiaireAvecId) {
		if (stagiaireAvecId != null) {
			em.merge(stagiaireAvecId);
		}
	}

	@Transactional
	@Override
	public void delete(Stagiaire s) {
		Stagiaire s1 = em.find(Stagiaire.class, s.getId());
		if (s1 != null) {
			em.remove(s1);
		}
	}

	@Override
	public Optional<Stagiaire> findById(Long id) {
		Stagiaire s = em.find(Stagiaire.class, id);
		return Optional.ofNullable(s);
	}

}

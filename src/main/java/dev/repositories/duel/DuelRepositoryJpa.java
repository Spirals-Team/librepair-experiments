package dev.repositories.duel;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import dev.entites.Duel;

public class DuelRepositoryJpa implements DuelRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Duel> findAll() {
		TypedQuery<Duel> duelsQuery = em.createQuery("FROM Duel d", Duel.class);
		return duelsQuery.getResultList();
	}

	@Override
	@Transactional
	public void save(Duel duel) {
		Duel d = new Duel();
		d.setStagiaireA(duel.getStagiaireA());
		d.setStagiaireB(duel.getStagiaireB());
		d.setQuizz(duel.getQuizz());
		em.persist(d);
	}

	@Override
	@Transactional
	public void update(Duel duelAvecId) {
		if (em.find(Duel.class, duelAvecId.getId()) != null)
			em.merge(duelAvecId);

	}

	@Override
	@Transactional
	public void delete(Duel duel) {
		if (em.find(Duel.class, duel.getId()) != null) {
			Duel d = em.find(Duel.class, duel.getId());
			em.remove(d);
		}

	}

	@Override
	public Optional<Duel> findById(Long id) {
		return Optional.ofNullable(em.find(Duel.class, id));
	}
}

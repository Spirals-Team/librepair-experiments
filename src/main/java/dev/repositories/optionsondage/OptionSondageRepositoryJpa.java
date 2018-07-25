package dev.repositories.optionsondage;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.entites.OptionSondage;

public class OptionSondageRepositoryJpa implements OptionSondageRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<OptionSondage> findAll() {
		TypedQuery<OptionSondage> options = em.createQuery("FROM OptionSondage os", OptionSondage.class);
		return options.getResultList();
	}

	@Override
	@Transactional
	public void save(OptionSondage entite) {
		OptionSondage os = new OptionSondage();
		os.setDescription(entite.getLibelle());
		os.setLibelle(entite.getLibelle());
		em.persist(os);
	}

	@Override
	@Transactional
	public void update(OptionSondage entiteAvecId) {
		if (em.find(OptionSondage.class, entiteAvecId.getId()) != null) {
			em.merge(entiteAvecId);
		}
	}

	@Override
	@Transactional
	public void delete(OptionSondage entite) {
		if (em.find(OptionSondage.class, entite.getId()) != null) {
			OptionSondage os = em.find(OptionSondage.class, entite.getId());
			em.remove(os);
		}
	}

	@Override
	public Optional<OptionSondage> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

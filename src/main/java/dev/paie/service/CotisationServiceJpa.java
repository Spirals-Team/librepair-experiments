package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Service
@Transactional
public class CotisationServiceJpa implements CotisationService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void sauvegarder(Cotisation nouvelleCotisation) {
		em.persist(nouvelleCotisation);
	}

	@Override
	public void mettreAJour(Cotisation cotisation) {
		em.merge(cotisation);

	}

	@Override
	public List<Cotisation> lister() {
		TypedQuery<Cotisation> query = em.createQuery("select p from Cotisation p", Cotisation.class);
		List<Cotisation> list = query.getResultList();
		return list;
	}

}

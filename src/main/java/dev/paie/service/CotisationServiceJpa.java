package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Transactional
@Service
public class CotisationServiceJpa implements CotisationService {

	@PersistenceContext
	private EntityManager em;

	public void sauvegarder(Cotisation nouvelleCotisation) {
		em.persist(nouvelleCotisation);
	}

	@Override
	public void mettreAJour(Cotisation cotisation) {
		em.merge(cotisation);

	}

	@Override
	public List<Cotisation> lister() {
		return em.createQuery("SELECT p FROM Cotisation p").getResultList();
	}

}

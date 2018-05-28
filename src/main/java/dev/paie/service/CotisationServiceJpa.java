package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Service
public class CotisationServiceJpa implements CotisationService {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void sauvegarder(Cotisation nouvelleCotisation) {
		em.persist(nouvelleCotisation);

	}

	@Override
	@Transactional
	public void mettreAJour(Cotisation cotisation) {
		em.merge(cotisation);
	}

	@Override
	@Transactional
	public List<Cotisation> lister() {

		return em.createQuery("SELECT c FROM Cotisation c").getResultList();
	}

	@Override
	@Transactional
	public void supprimer() {
		em.createQuery("DELETE FROM Cotisation").executeUpdate();

	}

}

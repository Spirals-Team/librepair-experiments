package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		// TODO Auto-generated method stub
		em.persist(nouvelleCotisation);
	}

	@Override
	@Transactional
	public void mettreAJour(Cotisation cotisation) {
		// TODO Auto-generated method stub
		em.find(Cotisation.class, cotisation.getId());
		em.merge(cotisation);
	}

	@Override
	@Transactional
	public List<Cotisation> lister() {
		// TODO Auto-generated method stub
		// List<Cotisation> listCotisation = new ArrayList<Cotisation>();
		TypedQuery<Cotisation> query = em.createQuery("SELECT p from Cotisation p", Cotisation.class);
		List<Cotisation> listCotisations = query.getResultList();
		return listCotisations;
	}

}

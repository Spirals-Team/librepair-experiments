package dev.paie.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		// TODO Auto-generated method stub
		em.persist(nouvelleCotisation);
	}

	@Override
	public void mettreAJour(Cotisation cotisation) {
		em.merge(cotisation);
	}

	@Override
	public List<Cotisation> lister() {
		return (List<Cotisation>) em.createQuery("select c from Cotisation c").getResultList();
	}
}

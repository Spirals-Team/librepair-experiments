package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Service
@Transactional
public class CotisationServiceJpa implements CotisationService {

    @PersistenceContext private EntityManager em;

	@Override
	public void sauvegarder(Cotisation nouvelleCotisation) {
		// TODO Auto-generated method stub
		// persit insertion - suppression
		em.persist(nouvelleCotisation);
		
	}

	@Override
	public void mettreAJour(Cotisation cotisation) {
		// TODO Auto-generated method stub
		// merge modification
		em.merge(cotisation);
	}

	@Override
	public List<Cotisation> lister() {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT p FROM Cotisation p ").getResultList();
	}

}

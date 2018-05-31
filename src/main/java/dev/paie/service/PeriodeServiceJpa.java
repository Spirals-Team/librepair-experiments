package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Periode;

@Service
public class PeriodeServiceJpa implements PeriodeService {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void sauvegarder(Periode nouvellePeriode) {
		em.persist(nouvellePeriode);
	}

	@Override
	public void mettreAJour(Periode periode) {
		em.merge(periode);

	}

	@Override
	public List<Periode> lister() {
		return (List<Periode>) em.createQuery("Select p from Periode p").getResultList();
	}

}

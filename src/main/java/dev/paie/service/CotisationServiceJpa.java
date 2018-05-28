package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Service
@Transactional
public class CotisationServiceJpa implements CotisationService {
	
	@PersistenceContext private EntityManager em;
	
	@Override
	public void sauvegarder(Cotisation nouvelleCotisation) {
		em.persist(nouvelleCotisation);
	}

	@Override
	public void mettreAJour(Cotisation cotisation) {
		
		Cotisation cotisationBdd = em.find(Cotisation.class, cotisation.getId());
		if(cotisationBdd != null)
		{
			cotisationBdd.setCode(cotisation.getCode());
			cotisationBdd.setLibelle(cotisation.getLibelle());
			cotisationBdd.setTauxSalarial(cotisation.getTauxSalarial());
			cotisationBdd.setTauxPatronal(cotisation.getTauxPatronal());
		}
	}

	@Override
	public List<Cotisation> lister() {
		Query query = em.createQuery("FROM Cotisation");
		List<Cotisation> listCotisation = (List<Cotisation>) query.getResultList();
		return listCotisation;
	}

}

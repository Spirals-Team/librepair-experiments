package dev.paie.services;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.ProfilRemuneration;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CotisationService cotisationServiceJpa;
	@Autowired
	GradeServiceJdbcTemplate gradeService;

	@Override
	@Transactional
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cotisations-imposables.xml",
				"cotisations-non-imposables.xml", "entreprises.xml", "grades.xml", "profils-remuneration.xml");
		Map<String, Cotisation> cots = context.getBeansOfType(Cotisation.class);
		for (Cotisation cot : cots.values()) {
			cotisationServiceJpa.sauvegarder(cot);
		}

		Map<String, Grade> grades = context.getBeansOfType(Grade.class);
		for (Grade g : grades.values()) {
			gradeService.sauvegarder(g);
		}

		Map<String, Entreprise> entreprises = context.getBeansOfType(Entreprise.class);
		for (Entreprise e : entreprises.values()) {
			em.persist(e);
		}

		Map<String, ProfilRemuneration> profils = context.getBeansOfType(ProfilRemuneration.class);
		for (ProfilRemuneration p : profils.values()) {
			em.persist(p);
		}
	}
}

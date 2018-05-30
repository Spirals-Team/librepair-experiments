package dev.paie.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@PersistenceContext
	@Autowired
	private EntityManager em;

	@Override
	@Transactional
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cotisations-imposables.xml",
				"cotisations-non-imposables.xml", "entreprises.xml", "grades.xml", "profils-remuneration.xml");

		Map<String, Cotisation> ctos = context.getBeansOfType(Cotisation.class);
		Collection<Cotisation> cotis = ctos.values();
		Map<String, Entreprise> entos = context.getBeansOfType(Entreprise.class);
		Collection<Entreprise> entis = entos.values();
		Map<String, Grade> grados = context.getBeansOfType(Grade.class);
		Collection<Grade> gradis = grados.values();
		Map<String, ProfilRemuneration> proRemLis = context.getBeansOfType(ProfilRemuneration.class);
		Collection<ProfilRemuneration> proRemColl = proRemLis.values();
		for (Cotisation cotisation : cotis) {
			em.persist(cotisation);
		}
		for (Entreprise entreprise : entis) {
			em.persist(entreprise);
		}
		for (Grade grade : gradis) {
			em.persist(grade);
		}
		for (ProfilRemuneration prof : proRemColl) {
			em.persist(prof);
		}

		List<Periode> annee = new ArrayList<>();
		int anneeCourante = LocalDate.now().getYear();
		for (int i = 1; i <= 12; i++) {
			Periode pp = new Periode();
			pp.setDateDebut(LocalDate.of(anneeCourante, i, 1));
			pp.setDateFin(YearMonth.of(anneeCourante, i).atEndOfMonth());
			annee.add(pp);
		}

		for (Periode p : annee) {
			em.persist(p);
		}

		context.close();

	}

}

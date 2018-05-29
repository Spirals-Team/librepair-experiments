package dev.paie.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	private EntityManager em;

	@Override
	@Transactional
	public void initialiser() {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("grades.xml", "entreprises.xml",
				"profils-remuneration.xml", "cotisations-imposables.xml", "cotisations-non-imposables.xml");

		Map<String, Cotisation> ctos = context.getBeansOfType(Cotisation.class);
		Collection<Cotisation> cotis = ctos.values();

		for (Cotisation cotisation : cotis) {
			em.persist(cotisation);
		}

		Map<String, Entreprise> ent = context.getBeansOfType(Entreprise.class);
		Collection<Entreprise> entre = ent.values();

		for (Entreprise entreprise : entre) {
			em.persist(entreprise);
		}

		Map<String, Grade> gra = context.getBeansOfType(Grade.class);
		Collection<Grade> grad = gra.values();

		for (Grade grade : grad) {
			em.persist(grade);
		}

		Map<String, ProfilRemuneration> prof = context.getBeansOfType(ProfilRemuneration.class);
		Collection<ProfilRemuneration> profil = prof.values();

		for (ProfilRemuneration profilRemuneration : profil) {
			em.persist(profilRemuneration);
		}

		// Periode

		for (int i = 1; i <= 12; i++) {
			Periode periode = new Periode();
			LocalDate dateDebut = LocalDate.of(2018, i, 1);
			periode.setDateDebut(dateDebut);
			int jourfin;
			if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
				jourfin = 31;
			} else if (i == 2) {
				jourfin = 28;
			} else {
				jourfin = 30;
			}
			LocalDate dateFin = LocalDate.of(2018, i, jourfin);
			periode.setDateFin(dateFin);
			em.persist(periode);
		}

		context.close();
	}
}

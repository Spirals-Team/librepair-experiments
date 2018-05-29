package dev.paie.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.repository.CotisationRepository;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.ProfilRemunerationRepository;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private CotisationRepository cotisationRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private PeriodeRepository periodeRepository;

	@Autowired
	private ProfilRemunerationRepository profilRemunerationRepository;

	@Override
	@Transactional
	public void initialiser() {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("init_config.xml");

		Collection<Entreprise> entreprises = ctx.getBeansOfType(Entreprise.class).values();
		for (Entreprise entreprise : entreprises) {
			entrepriseRepository.save(entreprise);
		}
		Collection<Grade> grades = ctx.getBeansOfType(Grade.class).values();
		for (Grade grade : grades) {
			gradeRepository.save(grade);
		}

		Collection<Cotisation> cotisations = ctx.getBeansOfType(Cotisation.class).values();
		for (Cotisation cotisation : cotisations) {
			cotisationRepository.save(cotisation);
		}

		Collection<ProfilRemuneration> profilRemunerations = ctx.getBeansOfType(ProfilRemuneration.class).values();
		for (ProfilRemuneration profilRemuneration : profilRemunerations) {
			profilRemunerationRepository.save(profilRemuneration);
		}

		for (int i = 0; i < 12; i++) {
			Periode p = new Periode();
			p.setDateDebut(LocalDate.of(LocalDate.now().getYear(), i + 1, 1));
			p.setDateFin(p.getDateDebut().with(TemporalAdjusters.lastDayOfMonth()));

			periodeRepository.save(p);

		}
		ctx.close();

	}

}

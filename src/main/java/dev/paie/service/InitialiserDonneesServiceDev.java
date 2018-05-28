package dev.paie.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

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

	private ClassPathXmlApplicationContext context;

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private ProfilRemunerationRepository profilRemunerationsRepository;

	@Autowired
	private CotisationRepository cotisationRepository;

	@Autowired
	private PeriodeRepository periodeRepository;

	private List<Entreprise> entreprises;

	private List<Grade> grades;

	private List<ProfilRemuneration> profilRemunerations;

	private List<Cotisation> cotisations;

	@Override
	public void initialiser() {

		context = new ClassPathXmlApplicationContext("init-config.xml");

		grades = new ArrayList<Grade>(context.getBeansOfType(Grade.class).values());

		profilRemunerations = new ArrayList<ProfilRemuneration>(
				context.getBeansOfType(ProfilRemuneration.class).values());

		cotisations = new ArrayList<Cotisation>(context.getBeansOfType(Cotisation.class).values());

		entreprises = new ArrayList<Entreprise>(context.getBeansOfType(Entreprise.class).values());

		context.close();

		for (ProfilRemuneration profilRemuneration : profilRemunerations) {
			profilRemunerationsRepository.save(profilRemuneration);
		}

		for (Grade grade : grades) {
			gradeRepository.save(grade);
		}

		for (Entreprise entreprise : entreprises) {
			entrepriseRepository.save(entreprise);
		}

		for (Cotisation cotisation : cotisations) {
			cotisationRepository.save(cotisation);
		}

		int anneeCourante = Calendar.getInstance().get(Calendar.YEAR);

		for (int i = 1; i < 13; i++) {
			Month mois = Month.of(i);
			LocalDate dateDebut = LocalDate.of(anneeCourante, mois, 01);
			LocalDate dateFin = LocalDate.of(anneeCourante, mois, dateDebut.lengthOfMonth());
			periodeRepository.save(new Periode(dateDebut, dateFin));
		}

	}

}

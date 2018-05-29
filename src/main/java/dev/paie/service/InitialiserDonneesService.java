package dev.paie.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import dev.paie.config.DataSourceMySQLConfig;
import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.CotisationRepository;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.ProfilRemunerationRepository;


@Service
public class InitialiserDonneesService implements DonneesService{

	private ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("init-config.xml");	    ;
	private List<Cotisation> cotisations = new ArrayList<Cotisation>(context.getBeansOfType(Cotisation.class).values());;
	private List<Entreprise> entreprises = new ArrayList<Entreprise>(context.getBeansOfType(Entreprise.class).values());;
	private List<Grade> enGrades = new ArrayList<Grade>(context.getBeansOfType(Grade.class).values());;
	private List<ProfilRemuneration> profilRemunerations = new ArrayList<ProfilRemuneration>(context.getBeansOfType(ProfilRemuneration.class).values());
	private List<Periode> periodes = new ArrayList<Periode>() ;
	
	@Autowired private CotisationRepository repoCotisation ;
	@Autowired private EntrepriseRepository repoEntreprise ;
	@Autowired private GradeRepository repoGrade;
	@Autowired private ProfilRemunerationRepository repoProfilRemuneration;
	@Autowired private PeriodeRepository repoPeriode;
	
	public void initialiser() {
		// TODO Auto-generated method stub
		 
		
		setPeriodes();
		for (Cotisation cotisation : cotisations) {
			repoCotisation.save(cotisation);
		}
		for (Entreprise entreprise : entreprises) {
			repoEntreprise.save(entreprise);
		}
		for (Grade grade : enGrades) {
			repoGrade.save(grade);
		}
		for (Periode periode : periodes) {
			repoPeriode.save(periode);
		}
		for (ProfilRemuneration profilRemuneration : profilRemunerations) {
			repoProfilRemuneration.save(profilRemuneration);
		}
			
	
		context.close();
	}

	
	
	public void setPeriodes(){
		
		int annee = Calendar.getInstance().get(Calendar.YEAR);
		
		for (int i = 1; i < 12; i++) {
			Periode periode = new Periode();
			LocalDate initial = LocalDate.of(annee, i, 1);
			LocalDate start = initial.withDayOfMonth(1);
			LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
			periode.setId(i);
			periode.setDateDebut(start);
			periode.setDateFin(end);
			periodes.add(periode);
		}
	}


}

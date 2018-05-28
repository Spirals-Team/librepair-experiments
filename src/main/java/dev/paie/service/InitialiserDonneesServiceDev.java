package dev.paie.service;

import java.time.LocalDate;
import java.util.Collection;

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
@Transactional
public class InitialiserDonneesServiceDev implements InitialiserDonneesService{
	
	@PersistenceContext private EntityManager em;
	
	@Override
	public void initialiser() {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cotisations-imposables.xml", "cotisations-non-imposables.xml", "entreprises.xml", "grades.xml", "profils-remuneration.xml");
		Collection<Cotisation> collecCotisations = context.getBeansOfType(Cotisation.class).values();
		Collection<Entreprise> collecEntreprises = context.getBeansOfType(Entreprise.class).values();
		Collection<Grade> collecGrades = context.getBeansOfType(Grade.class).values();
		Collection<ProfilRemuneration> collecProfilsRemuneration = context.getBeansOfType(ProfilRemuneration.class).values();
		
		insertCollec(collecCotisations);
		insertCollec(collecEntreprises);
		insertCollec(collecGrades);
		insertCollec(collecProfilsRemuneration);
		
		insertPeriode();
		
		context.close();
	}
	
	private <T> void insertCollec(Collection<T> collection)
	{
		for(T element : collection)
		{
			em.persist(element);
		}
	}
	
	private void insertPeriode()
	{
		LocalDate aujourdhui = LocalDate.now();
		LocalDate debutPeriode = LocalDate.of(aujourdhui.getYear(), 1, 1);
		
		while(debutPeriode.getYear() < aujourdhui.plusYears(1).getYear())
		{
			Periode periode = new Periode();
			periode.setDateDebut(debutPeriode);
			periode.setDateFin(debutPeriode.plusMonths(1).minusDays(1));
			
			em.persist(periode);
			
			debutPeriode = debutPeriode.plusMonths(1);
		}
	}
}

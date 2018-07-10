package dev.repositories.stagiaire;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import dev.entites.Stagiaire;

@Repository
public class StagiaireRepositoryMemoire implements StagiaireRepository {

	private List<Stagiaire> stagiaires = new ArrayList<>();

	/**
	 * Initialiser la liste des entites
	 */
	@PostConstruct
	public void initialiser() {
		// créer un contexte avec la classe ClassPathXmlApplicationContext
		// Récupérer les données via la méthode context.getBeansOfType(...)
		// fermer le contexte
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdd/jdd-stagiaire.xml")) {
			this.stagiaires = new ArrayList<>(context.getBeansOfType(Stagiaire.class).values());

		}

	}

	// implémente les méthodes du CRUD

	@Override
	public List<Stagiaire> findAll() {
		return this.stagiaires;
	}

	@Override
	public void save(Stagiaire stagiaire) {
		this.stagiaires.add(stagiaire);

	}

	@Override
	public void update(Stagiaire stagiaireAvecId) {
		Long id = stagiaireAvecId.getId();
		for (Stagiaire s : stagiaires) {
			if (id == stagiaireAvecId.getId()) {
				s.setPrenom(stagiaireAvecId.getPrenom());
				s.setNom(stagiaireAvecId.getNom());
				s.setEmail(stagiaireAvecId.getEmail());
				s.setPhotoUrl(stagiaireAvecId.getEmail());
			}
		}

	}

	@Override
	public void delete(Stagiaire stagiaire) {
		this.stagiaires.remove(stagiaire);

	}

}

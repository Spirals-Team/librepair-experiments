package dev.repositories.stagiaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.entites.Stagiaire;

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

			// pour chaque stagiaire existant on lui ajoute un id
			/*
			 * for (Stagiaire stagiaire : stagiaires) {
			 * stagiaire.setId(RandomUtils.nextLong()); }
			 */
		}

	}

	// implémente les méthodes du CRUD

	@Override
	public List<Stagiaire> findAll() {
		return this.stagiaires;
	}

	@Override
	public void save(Stagiaire stagiaireASauvegarder) {
		stagiaireASauvegarder.setId(RandomUtils.nextLong());
		this.stagiaires.add(stagiaireASauvegarder);

	}

	@Override
	public void update(Stagiaire stagiaireAvecId) {
		Long id = stagiaireAvecId.getId();
		for (Stagiaire s : stagiaires) {
			if (id.equals(s.getId())) {
				s.setPrenom(stagiaireAvecId.getPrenom());
				s.setNom(stagiaireAvecId.getNom());
				s.setEmail(stagiaireAvecId.getEmail());
				s.setPhotoUrl(stagiaireAvecId.getPhotoUrl());
			}
		}

	}

	@Override
	public void delete(Stagiaire stagiaire) {
		this.stagiaires.remove(stagiaire);

	}

	@Override
	public Optional<Stagiaire> findById(Long id) {
		return this.stagiaires.stream().filter(s -> s.getId().equals(id)).findFirst();
	}

}

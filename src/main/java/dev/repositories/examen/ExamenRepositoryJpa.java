package dev.repositories.examen;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dev.entites.Examen;

public class ExamenRepositoryJpa implements ExamenRepository {

	// Récupération d'une instance de l'Entity Manager
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Examen> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Examen entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Examen entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Examen entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Examen> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

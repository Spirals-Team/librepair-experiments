package dev.repositories.examen;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Examen;

@Repository
public class ExamenRepositoryDataJpa implements ExamenRepository {

	private ExamenDataJpaRepo entiteDataJpaRepo;

	public ExamenRepositoryDataJpa(ExamenDataJpaRepo entiteDataJpaRepo) {
		this.entiteDataJpaRepo = entiteDataJpaRepo;
	}

	@Override
	public List<Examen> findAll() {
		return entiteDataJpaRepo.findAll();
	}

	@Override
	public void save(Examen entite) {
		entiteDataJpaRepo.save(entite);
	}

	@Override
	public void update(Examen entiteAvecId) {
		if (entiteDataJpaRepo.existsById(entiteAvecId.getId())) {
			save(entiteAvecId);
		}
	}

	@Override
	public void delete(Examen entite) {
		entiteDataJpaRepo.delete(entite);
	}

	@Override
	public Optional<Examen> findById(Long id) {
		return entiteDataJpaRepo.findById(id);
	}

}

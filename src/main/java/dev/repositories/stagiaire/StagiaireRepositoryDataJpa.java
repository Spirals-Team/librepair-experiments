package dev.repositories.stagiaire;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Stagiaire;

@Repository
public class StagiaireRepositoryDataJpa implements StagiaireRepository {

	private StagiaireDataJpaRepo stagiaireDataJpaRepo;

	public StagiaireRepositoryDataJpa(StagiaireDataJpaRepo stagiaireDataJpaRepo) {
		this.stagiaireDataJpaRepo = stagiaireDataJpaRepo;
	}

	@Override
	public List<Stagiaire> findAll() {
		return stagiaireDataJpaRepo.findAll();
	}

	@Override
	public void save(Stagiaire s) {
		stagiaireDataJpaRepo.save(s);

	}

	@Override
	public void update(Stagiaire stagiaireAvecId) {
		if (stagiaireAvecId != null) {
			stagiaireDataJpaRepo.save(stagiaireAvecId);
		}

	}

	@Override
	public void delete(Stagiaire s) {
		stagiaireDataJpaRepo.delete(s);

	}

	@Override
	public Optional<Stagiaire> findById(Long id) {
		Optional<Stagiaire> s = stagiaireDataJpaRepo.findById(id);

		return s;
	}

}

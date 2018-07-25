package dev.repositories.concours;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Concours;

@Repository
public class ConcoursRepositoryDataJpa implements ConcoursRepository {

	private ConcoursDataJpaRepo ConcoursDataJpaRepo;

	public ConcoursRepositoryDataJpa(dev.repositories.concours.ConcoursDataJpaRepo concoursDataJpaRepo) {
		super();
		ConcoursDataJpaRepo = concoursDataJpaRepo;
	}

	@Override
	public List<Concours> findAll() {

		return ConcoursDataJpaRepo.findAll();
	}

	@Override
	public void save(Concours entite) {
		ConcoursDataJpaRepo.save(entite);

	}

	@Override
	public void update(Concours entiteAvecId) {
		if (entiteAvecId != null) {
			ConcoursDataJpaRepo.save(entiteAvecId);
		}

	}

	@Override
	public void delete(Concours entite) {
		ConcoursDataJpaRepo.delete(entite);

	}

	@Override
	public Optional<Concours> findById(Long id) {
		Optional<Concours> c = ConcoursDataJpaRepo.findById(id);
		return c;
	}

}

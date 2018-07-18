package dev.repositories.optionsondage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.OptionSondage;

@Repository
public class OptionSondageRepositoryDataJpa implements OptionSondageRepository {

	private OptionSondageDataJpaRepository entiteDataJpa;

	public OptionSondageRepositoryDataJpa(OptionSondageDataJpaRepository entiteDataJpa) {
		super();
		this.entiteDataJpa = entiteDataJpa;
	}

	@Override
	public List<OptionSondage> findAll() {
		return entiteDataJpa.findAll();
	}

	@Override
	public void save(OptionSondage entite) {
		entiteDataJpa.save(entite);
	}

	@Override
	public void update(OptionSondage entiteAvecId) {
		// TODO Auto-generated method stub
		entiteDataJpa.save(entiteAvecId);
	}

	@Override
	public void delete(OptionSondage entite) {
		// TODO Auto-generated method stub
		entiteDataJpa.delete(entite);
	}

	@Override
	public Optional<OptionSondage> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

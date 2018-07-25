package dev.repositories.sondage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Sondage;

@Repository
public class SondageRepositoryDataJpa implements SondageRepository {

	private SondageDataJpaRepository sondageDataJpa;

	public SondageRepositoryDataJpa(SondageDataJpaRepository sondageDataJpa) {
		super();
		this.sondageDataJpa = sondageDataJpa;
	}

	@Override
	public List<Sondage> findAll() {
		// TODO Auto-generated method stub
		return sondageDataJpa.findAll();
	}

	@Override
	public void save(Sondage entite) {
		// TODO Auto-generated method stub
		sondageDataJpa.save(entite);
	}

	@Override
	public void update(Sondage entiteAvecId) {
		// TODO Auto-generated method stub
		sondageDataJpa.save(entiteAvecId);
	}

	@Override
	public void delete(Sondage entite) {
		// TODO Auto-generated method stub
		sondageDataJpa.delete(entite);
	}

	@Override
	public Optional<Sondage> findById(Long id) {
		// TODO Auto-generated method stub
		return sondageDataJpa.findById(id);
	}

}

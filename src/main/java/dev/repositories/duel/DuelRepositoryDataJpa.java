package dev.repositories.duel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Duel;

@Repository
public class DuelRepositoryDataJpa implements DuelRepository {

	private DuelDataJpaRepository duelDataJpaRepository;

	public DuelRepositoryDataJpa(DuelDataJpaRepository duelDataJpaRepository) {
		this.duelDataJpaRepository = duelDataJpaRepository;
	}

	@Override
	public List<Duel> findAll() {
		return duelDataJpaRepository.findAll();
	}

	@Override
	public void save(Duel duel) {
		duelDataJpaRepository.save(duel);

	}

	@Override
	public void update(Duel duelAvecId) {
		duelDataJpaRepository.save(duelAvecId);

	}

	@Override
	public void delete(Duel duel) {
		duelDataJpaRepository.delete(duel);

	}

	@Override
	public Optional<Duel> findById(Long id) {
		return duelDataJpaRepository.findById(id);
	}

}

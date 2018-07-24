package dev.repositories.quizz;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.entites.Quizz;

@Repository
public class QuizzRepositoryDataJpa implements QuizzRepository {

	private QuizzDataJpaRepo quizzDataJpaRepo;

	public QuizzRepositoryDataJpa(QuizzDataJpaRepo quizzDataJpaRepo) {
		this.quizzDataJpaRepo = quizzDataJpaRepo;
	}

	@Override
	public List<Quizz> findAll() {
		return quizzDataJpaRepo.findAll();
	}

	@Override
	public void save(Quizz entite) {
		quizzDataJpaRepo.save(entite);

	}

	@Override
	public void update(Quizz entiteAvecId) {
		if (quizzDataJpaRepo.existsById(entiteAvecId.getId())) {
			save(entiteAvecId);
		}

	}

	@Override
	public void delete(Quizz entite) {
		quizzDataJpaRepo.delete(entite);

	}

	@Override
	public Optional<Quizz> findById(Long id) {
		return quizzDataJpaRepo.findById(id);
	}

}

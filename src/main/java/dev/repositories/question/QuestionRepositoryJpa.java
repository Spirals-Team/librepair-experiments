package dev.repositories.question;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import dev.entites.Question;

@Transactional
public class QuestionRepositoryJpa implements QuestionRepository {

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Question entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Question entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Question entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Question> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

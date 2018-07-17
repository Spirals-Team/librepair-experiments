package dev.repositories.question;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import dev.entites.OptionQuestion;

@Transactional
public class OptionQuestionRepositoryJpa implements OptionQuestionRepository {

	// injecter une instance d'EntityManager
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<OptionQuestion> findAll() {
		// TODO Auto-generated method stub

		TypedQuery<OptionQuestion> querySecond = em.createQuery(" select opt from OptionQuestion opt",
				OptionQuestion.class);
		List<OptionQuestion> res = querySecond.getResultList();

		return res;
	}

	@Override
	public void save(OptionQuestion entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(OptionQuestion entiteAvecId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(OptionQuestion entite) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<OptionQuestion> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

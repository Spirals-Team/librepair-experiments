package dev.repositories.quizz;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.entites.Quizz;
import dev.repositories.BaseRepositoryMemoire;

@Service
public class QuizzRepositoryMemoire extends BaseRepositoryMemoire<Quizz> implements QuizzRepository {

	public QuizzRepositoryMemoire(@Value("${jdd.quizz}") String path) {
		super(path, Quizz.class);
	}

	@Override
	public Optional<Quizz> findById(Long id) {
		return this.getEntityList().stream().filter(s -> s.getId().equals(id)).findFirst();
	}

}
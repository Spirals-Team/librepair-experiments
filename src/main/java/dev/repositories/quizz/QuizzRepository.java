package dev.repositories.quizz;

import java.util.Optional;

import dev.entites.Quizz;
import dev.repositories.CrudRepository;

public interface QuizzRepository extends CrudRepository<Quizz> {

	Optional<Quizz> findById(Long id);

}

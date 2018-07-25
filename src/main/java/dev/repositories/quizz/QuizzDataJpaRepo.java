package dev.repositories.quizz;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Quizz;

public interface QuizzDataJpaRepo extends JpaRepository<Quizz, Long> {

}

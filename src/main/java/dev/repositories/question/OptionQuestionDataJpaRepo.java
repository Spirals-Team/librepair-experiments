package dev.repositories.question;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.OptionQuestion;

public interface OptionQuestionDataJpaRepo extends JpaRepository<OptionQuestion, Long> {

}

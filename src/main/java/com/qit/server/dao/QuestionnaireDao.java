package com.qit.server.dao;

import com.qit.server.models.quiz.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireDao extends JpaRepository<Questionnaire, Long> {
}

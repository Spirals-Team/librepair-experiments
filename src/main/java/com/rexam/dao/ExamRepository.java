package com.rexam.dao;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.Exam;

public interface ExamRepository extends CrudRepository<Exam, String>{

}

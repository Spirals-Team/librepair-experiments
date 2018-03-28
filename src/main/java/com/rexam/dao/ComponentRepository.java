package com.rexam.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.Component;
import com.rexam.model.Exam;

public interface ComponentRepository extends CrudRepository<Component, Integer>{

    public List<Component> findByExam(Exam exam);
}

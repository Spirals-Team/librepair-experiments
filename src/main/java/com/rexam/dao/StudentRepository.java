package com.rexam.dao;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.Student;

public interface StudentRepository extends CrudRepository<Student, String>{
	
	Student findByEmail(String email);
}

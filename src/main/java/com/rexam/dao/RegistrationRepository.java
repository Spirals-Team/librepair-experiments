package com.rexam.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.IdRegistration;
import com.rexam.model.Registration;
import com.rexam.model.StudentYear;

public interface RegistrationRepository extends CrudRepository<Registration, IdRegistration>{


	public List<Registration> findByIdCodeTeachingUnit(String codeTeachingUnit);

	public List<Registration> findAllByOrderByIdAsc();
	
	public List<Registration> findByStudentYear (StudentYear studentYear);
}

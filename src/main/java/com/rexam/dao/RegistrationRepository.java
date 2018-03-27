package com.rexam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rexam.model.IdRegistration;
import com.rexam.model.Registration;
import com.rexam.model.StudentYear;
import com.rexam.model.TeachingUnit;

public interface RegistrationRepository extends CrudRepository<Registration, IdRegistration>{


	public List<Registration> findByIdCodeTeachingUnit(String codeTeachingUnit);

	public List<Registration> findAllByOrderByIdAsc();
	
	public List<Registration> findByStudentYear (StudentYear studentYear);
	
	@Query(value="select tu from Registration reg, TeachingUnit tu where reg.teachingUnit = tu order by tu.discipline ASC")
	public List<TeachingUnit> findTeachingUnits();
	
	@Query(value="select DISTINCT tu.discipline from Registration reg, TeachingUnit tu where reg.teachingUnit = tu ")
    public List<String> findDisciplines();
}

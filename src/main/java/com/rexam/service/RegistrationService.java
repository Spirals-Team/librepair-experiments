package com.rexam.service;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rexam.dao.CurrentYearRepository;
import com.rexam.dao.RegistrationRepository;
import com.rexam.dao.ResultRepository;
import com.rexam.dao.StudentRepository;
import com.rexam.dao.TeachingUnitRepository;
import com.rexam.model.Component;
import com.rexam.model.CurrentYear;
import com.rexam.model.IdRegistration;
import com.rexam.model.IdResult;
import com.rexam.model.IdStudentYear;
import com.rexam.model.Registration;
import com.rexam.model.Result;
import com.rexam.model.Student;
import com.rexam.model.StudentYear;
import com.rexam.model.TeachingUnit;

/**
 * @author Arthur
 *
 */
@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;
	@Autowired
	private TeachingUnitRepository teachingUnitRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ResultRepository resultRepository;
	@Autowired
	private CurrentYearRepository currentYearRepository;

	
	/**
	 * This method set a registration for the student on the teaching unit
	 * Generates empty result for each exam in the teaching unit
	 * @param email
	 * @param codeUe
	 * @throws Exception
	 */
	public void registration(String email, String codeUe) throws Exception{

		TeachingUnit teachingUnit = teachingUnitRepository.findOne(codeUe);
		if (teachingUnit == null) {
			throw new Exception("Mauvaise UE");
		}
		Student student = studentRepository.findOne(email);
		if (student == null) {
			throw new Exception("Mauvais mail");
		}
		IdStudentYear ids = new IdStudentYear();
		//Find the first element of current_year table aka the current year omg
		int year = ((Collection<CurrentYear>) currentYearRepository.findAll()).stream().findFirst().get().getYear();
		ids.setYear(year);
		ids.setId(student.getId());
		
		StudentYear sYear = new StudentYear();
		sYear.setStudent(student);
		sYear.setId(ids);
		
		IdRegistration idr = new IdRegistration();
		idr.setCodeTeachingUnit(codeUe);
		idr.setIdStudentYear(ids);
		Registration reg = new Registration();
		reg.setId(idr);
		reg.setStudentYear(sYear);
		reg.setTeachingUnit(teachingUnit);
		reg.setStatus("Pas de Notes");
		registrationRepository.save(reg);
		List<Component> compos = teachingUnit.getComponents();
		for (Component component : compos) {

			IdResult idres = new IdResult();
			idres.setIdStudentYear(ids);
			idres.setCodeExam(component.getExam().getCode());

			Result res = new Result();
			res.setId(idres);
			res.setStudentYear(sYear);
			res.setExam(component.getExam());
			res.setScore(0.0);
			resultRepository.save(res);
		}

		registrationRepository.save(reg);

	}
}

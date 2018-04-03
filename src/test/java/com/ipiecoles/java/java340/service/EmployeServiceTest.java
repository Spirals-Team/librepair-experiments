package com.ipiecoles.java.java340.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.ipiecoles.java.java340.SpringWebApplication;
import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.repository.EmployeRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest{
	@InjectMocks
	private EmployeService employeService;
	
	@Mock
	private EmployeRepository employeRepository;
	//On utilise la version mocké de employeRepository car repository ey spring (@autowired)
	
	@Test(expected = EntityNotFoundException.class)
	public void testFindByMatriculeNotFound()
	{
		//Given
		Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(null);
		//When
		employeService.findByMatricule("C12345");
		//Then
	}
	
	@Test
	public void testFindByMatriculeFound()throws EmployeException
	{
		//Given
		Commercial c = new Commercial();
		Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(c);
		//When
		Employe employe = employeService.findByMatricule("C12345");
		//Then
		//Manque comparaison employe commercial
		
	}
}


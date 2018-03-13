package com.ipiecoles.java.java350.service;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;


@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest {
	
	@InjectMocks
	private EmployeService employeService;
	
	@Mock
	private EmployeRepository employeRepository;
	
	@Test
	public void testFindByMatriculeFound(){
		//GIVEN
		Commercial commercial = new Commercial();
		Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(commercial);
		
		//WHEN
		Employe employe = employeService.findByMatricule("C12345");
		
		
		//THEN
		Assertions.assertThat(employe).isEqualTo(commercial);
	}
	
	@Test
	public void testFindByMatriculeNotFound(){
		//GIVEN
		Commercial commercial = new Commercial();
		Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(null);
		

		//WHEN
		
		
		try {
			Employe employe = employeService.findByMatricule("C12345");
			Assertions.fail("ca aurait du planter");
		}
		catch(Exception e){
			//THEN
			Assertions.assertThat(e).isInstanceOf(EntityNotFoundException.class);
		}
		
	}


}

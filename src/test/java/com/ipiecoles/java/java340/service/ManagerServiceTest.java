package com.ipiecoles.java.java340.service;

import static org.mockito.AdditionalAnswers.*;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ipiecoles.java.java340.model.Manager;
import com.ipiecoles.java.java340.model.Technicien;
import com.ipiecoles.java.java340.repository.ManagerRepository;
import com.ipiecoles.java.java340.repository.TechnicienRepository;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {
	
	@InjectMocks
	private ManagerService managerService;
	
	@Mock
	private ManagerRepository managerRepository;
	
	@Mock
    public TechnicienRepository technicienRepository;
	
	@Test
	public void testaddTechniciens(){
		//Given
		Technicien tech = new Technicien("Durand", "Jean", "T12345", new LocalDate(), 1500d, 3);
		Manager man = new Manager("Dupond", "Jacques", "M12345", new LocalDate(), 2500d, new HashSet<>());
		
		Mockito.when(managerRepository.findOneWithEquipeById(Mockito.anyLong())).thenReturn(man);
		Mockito.when(technicienRepository.findByMatricule(Mockito.anyString())).thenReturn(tech);
		
		Mockito.when(technicienRepository.save(Mockito.any(Technicien.class))).then(returnsFirstArg());
		Mockito.when(managerRepository.save(Mockito.any(Manager.class))).then(returnsFirstArg());
		
		//When
		managerService.addTechniciens(2L, "T12345");
		
		//Then
		Mockito.verify(managerRepository, Mockito.times(1)).findOneWithEquipeById(2L);
		Mockito.verify(technicienRepository, Mockito.times(1)).findByMatricule("T12345");
		
		ArgumentCaptor<Manager> managerCaptor = ArgumentCaptor.forClass(Manager.class);
	    ArgumentCaptor<Technicien> technicienCaptor = ArgumentCaptor.forClass(Technicien.class);
	    
	    Mockito.verify(managerRepository).save(managerCaptor.capture());
	    Assertions.assertThat(managerCaptor.getValue().getEquipe()).contains(tech);
	    
	    Mockito.verify(technicienRepository).save(technicienCaptor.capture());
	    Assertions.assertThat(technicienCaptor.getValue().getManager()).isEqualTo(man);
	    
	    
	    
	}

}

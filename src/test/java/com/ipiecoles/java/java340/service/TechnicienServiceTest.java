package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.model.Manager;
import com.ipiecoles.java.java340.model.Technicien;
import com.ipiecoles.java.java340.repository.ManagerRepository;
import com.ipiecoles.java.java340.repository.TechnicienRepository;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@RunWith(MockitoJUnitRunner.class)
public class TechnicienServiceTest {
	
    @InjectMocks
    public TechnicienService technicienService;

    @Mock
    public TechnicienRepository technicienRepository;

    @Mock
    public ManagerRepository managerRepository;

    @Test
	public void testaddManager(){
		//Given
		Technicien technicien = new Technicien("Durand", "Jean", "T12345", new LocalDate(), 1500d, 3);
		Manager manager = new Manager("Dupond", "Jacques", "M12345", new LocalDate(), 2500d, new HashSet<>());
		
		Mockito.when(technicienRepository.findOne(Mockito.anyLong())).thenReturn(technicien);
		Mockito.when(managerRepository.findByMatricule(Mockito.anyString())).thenReturn(manager);
		Mockito.when(managerRepository.save(Mockito.any(Manager.class))).then(returnsFirstArg());
		Mockito.when(technicienRepository.save(Mockito.any(Technicien.class))).then(returnsFirstArg());
		
		
		//When
		technicienService.addManager(1L, "M12345");
		
		//Then
		Mockito.verify(technicienRepository, Mockito.times(1)).findOne(1L);
		Mockito.verify(managerRepository, Mockito.times(1)).findByMatricule("M12345");
		
	    ArgumentCaptor<Manager> managerCaptor = ArgumentCaptor.forClass(Manager.class);
	    ArgumentCaptor<Technicien> technicienCaptor = ArgumentCaptor.forClass(Technicien.class);
	    Mockito.verify(managerRepository).save(managerCaptor.capture());
	    Assertions.assertThat(managerCaptor.getValue().getEquipe()).contains(technicien);
	    Mockito.verify(technicienRepository).save(technicienCaptor.capture());
	    Assertions.assertThat(technicienCaptor.getValue().getManager()).isEqualTo(manager);
	}


}

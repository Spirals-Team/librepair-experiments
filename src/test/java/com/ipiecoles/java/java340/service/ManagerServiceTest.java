package com.ipiecoles.java.java340.service;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
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
	private TechnicienRepository technicienRepository;
	
	@Test(expected = EntityNotFoundException.class)
	public void addTechnicienTestWithWrongManagerId() {
		Mockito.when(managerRepository.findOneWithEquipeById(Mockito.anyLong())).thenReturn(null);
		managerService.addTechniciens(1l, "M14789");
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void addTechnicienTestWithWrongTechnicianMatricule() {
		Mockito.when(technicienRepository.findByMatricule(Mockito.anyString())).thenReturn(null);
		managerService.addTechniciens(1l, "M14789");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addTechnicienTestWithTechnicianAlreadyManaged() {
		Technicien returnedTechnician = new Technicien();
		returnedTechnician.setManager(new Manager());
		
		Mockito.when(managerRepository.findOneWithEquipeById(Mockito.anyLong())).thenReturn(new Manager());
		Mockito.when(technicienRepository.findByMatricule(Mockito.anyString())).thenReturn(returnedTechnician);
		
		managerService.addTechniciens(1l, "M14789");
	}
	
	@Test
	public void addTechnicienTestWithCorrectValues() {
		Technicien technicien = new Technicien();
		Manager manager = new Manager();

		
		Mockito.when(managerRepository.findOneWithEquipeById(Mockito.anyLong())).thenReturn(manager);
		Mockito.when(technicienRepository.findByMatricule(Mockito.anyString())).thenReturn(technicien);
		Mockito.when(managerRepository.save(manager)).thenReturn(manager);
		Mockito.when(technicienRepository.save(technicien)).thenReturn(technicien);
		
		// Lien existant entre Manager et technicien?
		Assertions.assertThat(technicien.getManager()).isNull();
		Assertions.assertThat(manager.getEquipe()).isEmpty();
		
		managerService.addTechniciens(1l, "M14789");
		
		ArgumentCaptor<Manager> managerCaptor = ArgumentCaptor.forClass(Manager.class);
		ArgumentCaptor<Technicien> technicienCaptor = ArgumentCaptor.forClass(Technicien.class);
		
		Mockito.verify(technicienRepository, Mockito.times(1)).save(technicienCaptor.capture());
		Mockito.verify(managerRepository, Mockito.times(1)).save(managerCaptor.capture());
		
		Assertions.assertThat(technicienCaptor.getValue().getManager()).isEqualTo(manager);
		Assertions.assertThat(managerCaptor.getValue().getEquipe()).contains(technicien);
	}
}
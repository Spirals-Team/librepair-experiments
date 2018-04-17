package com.ipiecoles.java.java340.service;

import java.util.HashSet;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
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
public class TechnicienServiceTest {
	
	@InjectMocks
	public TechnicienService technicienService;
	
	@Mock
	public TechnicienRepository technicienRepository;
	
	@Mock
	public ManagerRepository managerRepository;
	
	@Test
	public void testAddManager(){
		
		//Given
		final String  MATRICULE= "M12345";
		final Long ID_TECHNICIEN = 1L;
		
		Technicien technicien = new Technicien("Sona", "Karma", "M49898", new LocalDate(), 2500d, 3) ;
		Manager manager = new Manager("Leona", "Diana", MATRICULE, new LocalDate(), 2500d, new HashSet<Technicien>());
		
		// TEST Mock Bonnes Valeurs
		Mockito.when(technicienRepository.findOne(ID_TECHNICIEN)).thenReturn(technicien);
		Mockito.when(managerRepository.findByMatricule(MATRICULE)).thenReturn(manager);
		
		// Manager retourné si affecté à un technicien
		Mockito.when(managerRepository.save(Mockito.any(Manager.class))).then(AdditionalAnswers.returnsFirstArg());
		Mockito.when(technicienRepository.save(Mockito.any(Technicien.class))).then(AdditionalAnswers.returnsFirstArg());

		// WHEN
		technicienService.addManager(ID_TECHNICIEN, MATRICULE);
		
		// THEN
		// Bon Manager du Technicien? 
		Assertions.assertThat(technicien.getManager()).isEqualTo(manager);
		
		// Equipe Manager contient Technicien?
		Assertions.assertThat(manager.getEquipe()).hasSize(1);
		
		// Ajout Technicien à Manager?
		Assertions.assertThat(manager.getEquipe()).contains(technicien);
		
		// Méthodes appelées au moins 1 fois?
		Mockito.verify(technicienRepository, Mockito.times(1)).findOne(ID_TECHNICIEN);
		Mockito.verify(managerRepository, Mockito.times(1)).findByMatricule(MATRICULE);
		
		// Technicien contient bien manager?
		ArgumentCaptor<Technicien> technicienCaptor = ArgumentCaptor.forClass(Technicien.class);
		Mockito.verify(technicienRepository).save(technicienCaptor.capture());
		Assertions.assertThat(technicienCaptor.getValue().getManager()).isEqualTo(manager);
		
	}
	

}
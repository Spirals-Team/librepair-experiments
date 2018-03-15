package com.ipiecoles.java.java340.service;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.eclipse.jdt.internal.compiler.ast.Argument;
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
	public TechnicienService technicienS;
	
	@Mock
	public TechnicienRepository technicienR;
	
	@Mock
	public ManagerRepository managerR;
	
	@Test
	public void testAddManager() {
		//Given
		final String MATRICULE_T = "M12345";
		final String MATRICULE_M = "M98765";
		final Long ID_TECHNICIEN = 1L;
		Technicien t = new Technicien("Durand","Paul",MATRICULE_T,new LocalDate(), 1500d,3);
		Manager m = new Manager("Ducon","Pierre",MATRICULE_M,new LocalDate(), 2500d,new HashSet<>());
		Mockito.when(technicienR.findOne(ID_TECHNICIEN)).thenReturn(t);
		Mockito.when(managerR.findByMatricule(MATRICULE_M)).thenReturn(m);
		Mockito.when(managerR.save(Mockito.any(Manager.class))).then(AdditionalAnswers.returnsFirstArg());
		Mockito.when(technicienR.save(Mockito.any(Technicien.class))).then(AdditionalAnswers.returnsFirstArg());
		
		//When
		Manager manager = technicienS.addManager(ID_TECHNICIEN, MATRICULE_M);
		
		//Then		
		Mockito.verify(technicienR,Mockito.times(1)).findOne(ID_TECHNICIEN);
		Mockito.verify(managerR,Mockito.times(1)).findByMatricule(MATRICULE_M);
		Assertions.assertThat(m.getEquipe()).hasSize(1);
		Assertions.assertThat(m.getEquipe().contains(t));
		Assertions.assertThat(t.getManager()).isEqualTo(m);
		
		// permet de récuperer l'instance de Technicien lors de l'appel de addMAnager sur 
		//technicienS(voir la méthode qui ne return que m et non le techcnicien)
		ArgumentCaptor<Technicien> techncienCaptor = ArgumentCaptor.forClass(Technicien.class);
		Mockito.verify(technicienR).save(techncienCaptor.capture());
		Assertions.assertThat(techncienCaptor.getValue().getManager()).isEqualTo(m);
		
	}
	
	

}

package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.model.Manager;
import com.ipiecoles.java.java340.model.Technicien;
import com.ipiecoles.java.java340.repository.ManagerRepository;
import com.ipiecoles.java.java340.repository.TechnicienRepository;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.AdditionalAnswers.returnsFirstArg;



import java.util.HashSet;

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
       final String MATRICULE = "M12345";
       final Long ID_TECHNICIEN = 1L;

       Technicien technicien = new Technicien("Durand","Jean","T12345", new LocalDate(),
               1500d,3);
       Manager manager = new Manager("Dupond","Jacques",MATRICULE,new LocalDate(),
               2500d, new HashSet<>());
       Mockito.when(technicienRepository.findOne(ID_TECHNICIEN)).thenReturn(technicien);
       Mockito.when(managerRepository.findByMatricule(MATRICULE)).thenReturn(manager);
       Mockito.when(managerRepository.save(Mockito.any(Manager.class))).then(returnsFirstArg());
       Mockito.when(technicienRepository.save(Mockito.any(Technicien.class))).then(returnsFirstArg());

       //When
       technicienService.addManager(ID_TECHNICIEN, MATRICULE);

       //Then
       Mockito.verify(technicienRepository, Mockito.times(1)).findOne(ID_TECHNICIEN);
       Mockito.verify(managerRepository, Mockito.times(1)).findByMatricule(MATRICULE);

       Assertions.assertThat(manager.getEquipe()).hasSize(1);
       Assertions.assertThat(manager.getEquipe()).contains(technicien);

       Assertions.assertThat(technicien.getManager()).isEqualTo(manager);

       ArgumentCaptor<Technicien> technicienCaptor = ArgumentCaptor.forClass(Technicien.class);
       Mockito.verify(technicienRepository).save(technicienCaptor.capture());
       Assertions.assertThat(technicienCaptor.getValue().getManager()).isEqualTo(manager);
   }

}

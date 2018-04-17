package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    public void testFindByMatriculeNotFound(){
        //Given
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(null);

        //When
        try {
            employeService.findByMatricule("inconnu");
            Assertions.fail("Le test aurait dû lever une exception");
        }
        catch (EntityNotFoundException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("Impossible de trouver l'employé de matricule inconnu");
        }
    }

    @Test
    public void testFindByMatriculeFound(){
        //Given
        Commercial c = new Commercial();
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(c);

        //When
        Employe e = employeService.findByMatricule("connu");
        Assertions.assertThat(e).isEqualTo(c);
    }

}
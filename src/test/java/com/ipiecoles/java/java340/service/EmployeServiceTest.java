package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.repository.EmployeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test(expected = EntityNotFoundException.class)
    public void testfindByMatricule(){
        //Given
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(null);

        //When
        employeService.findByMatricule("C12345");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testfindByMatriculeFound() throws EmployeException{
        //Given
        Commercial commercial = new Commercial();
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(commercial);

        //When
        Employe employe = employeService.findByMatricule("connu");
        org.assertj.core.api.Assertions.assertThat(employe).isEqualTo(commercial);

    }

}

// Pour aller plus loin regarder Les TestSuite
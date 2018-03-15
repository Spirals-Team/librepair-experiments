package com.ipiecoles.java.java340.service;

import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.model.maker.CommercialMaker;
import com.ipiecoles.java.java340.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
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
    public void testFindByMatriculeNotFound(){
        //Given
        Mockito.when(employeRepository.findByMatricule("C12345")).thenReturn(null);

        //When
        employeService.findByMatricule("C12345");

        //Then exception
    }

    @Test
    public void test() {
        //Given
        Mockito.when(employeRepository.test()).thenReturn(5);

        //When
        Integer integer = employeService.testService();
        Assertions.assertThat(integer).isEqualTo(5);
    }

    @Test
    public void testFindByMatriculeFound() throws EmployeException {
        //Given
        Commercial c = CommercialMaker.aCommercial().build();
        Mockito.when(employeRepository.findByMatricule(Mockito.anyString())).thenReturn(c);

        //When
        Employe e = employeService.findByMatricule("connu");
        Assertions.assertThat(e).isEqualTo(c);
    }
}

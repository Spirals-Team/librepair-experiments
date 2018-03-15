package com.ipiecoles.java.java340.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ipiecoles.java.java340.SpringWebApplication;
import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebApplication.class)
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    Commercial pierreDurand, jeanJacques, jacquesDupond, julienPinteaux;

    @Before
    public void setUp() throws EmployeException {
        employeRepository.deleteAll();
        pierreDurand = new Commercial("Durand", "Pierre", "C12345", new LocalDate(), 1500d, 0d,0);
        jeanJacques = new Commercial("Jean-Jacques", "Jean", "C12346", new LocalDate(), 1500d, 0d,0);
        jacquesDupond = new Commercial("Dupond", "Jean-Jacques", "C12347", new LocalDate(), 1500d, 0d,0);
        julienPinteaux= new Commercial("Pinteaux", "Julien", "C12399", new LocalDate(), 1800d, 0d,0);

        pierreDurand = employeRepository.save(pierreDurand);
        jeanJacques = employeRepository.save(jeanJacques);
        jacquesDupond = employeRepository.save(jacquesDupond);
        julienPinteaux = employeRepository.save(julienPinteaux);
    }

    @After
    public void tearDown(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindByNomOrPrenomAllIgnoreCasePrenom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");

        //Then
            
        Assertions.assertThat(employes).hasSize(1);
        Assertions.assertThat(employes).contains(pierreDurand);

    }

    @Test
    public void testFindByNomOrPrenomAllIgnoreCaseNom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("durand");
        Assertions.assertThat(employes).hasSize(1);
        Assertions.assertThat(employes).contains(pierreDurand);
    }

    @Test
    public void testFindByNomOrPrenomAllIgnoreCaseNomPrenom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("Jean-jacques");
        Assertions.assertThat(employes).hasSize(2);
        Assertions.assertThat(employes).contains(jeanJacques, jacquesDupond);
    }

    @Test
    public void testFindByNomOrPrenomAllIgnoreCaseNotFound(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("toto");
        Assertions.assertThat(employes).isEmpty();
    }
    
    @Test
    public void testFindEmployePlusRiche(){
        //Given
    	
        //When    	
        List<Employe> employes = employeRepository.findEmployePlusRiches();
        Assertions.assertThat(employes).hasSize(1);
        for (Employe e : employes) {
        	Assertions.assertThat(e.getSalaire()).isGreaterThan(2000);   	
        }
     }
}

package com.ipiecoles.java.java340.repository;

import com.ipiecoles.java.java340.SpringWebApplication;
import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.model.maker.CommercialMaker;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest(classes = SpringWebApplication.class)
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository employeRepository;

    Commercial pierreDurand,jeanJacques,jacquesDupond;

    @Before
    public void setUp() throws EmployeException{
        employeRepository.deleteAll();

        pierreDurand = new Commercial("Durand","Pierre","C12345", new LocalDate(),
                1500d,0d,0);
        jeanJacques = new Commercial("Jacques","Jean","C12346", new LocalDate(),
                1500d,0d,0);
        jacquesDupond = new Commercial("Dupond","Jacques","C12347", new LocalDate(),
                1500d,0d,0);

        pierreDurand = employeRepository.save(pierreDurand);
        //pierreDurand = employeRepository.save(CommercialMaker.aCommercial().withPrenom("test").build());
        jeanJacques = employeRepository.save(jeanJacques);
        jacquesDupond = employeRepository.save(jacquesDupond);

    }

    @After
    public void tearDown(){
        employeRepository.deleteAll();
    }

    @Test
    public void testfindByNomOrPrenomAllIgnoreCasePrenom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");

        //Then
        Assertions.assertThat(employes).hasSize(1);
        Assertions.assertThat(employes).contains(pierreDurand);
    }

    @Test
    public void testfindByNomOrPrenomAllIgnoreCaseNom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("durand");

        //Then
        Assertions.assertThat(employes).hasSize(1);
        Assertions.assertThat(employes).contains(pierreDurand);
    }

    @Test
    public void testfindByNomOrPrenomAllIgnoreCaseNomPrenom(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("jacques");

        //Then
        Assertions.assertThat(employes).hasSize(2);
        Assertions.assertThat(employes).contains(jeanJacques,jacquesDupond);
    }

    @Test
    public void testfindByNomOrPrenomAllIgnoreCaseNotFound(){
        //Given

        //When
        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("toto");

        //Then
        Assertions.assertThat(employes).isEmpty();
    }
}


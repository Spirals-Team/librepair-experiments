package com.ipiecole.java.java360.repository;

import com.ipiecoles.java.java350.MySpringApplication;
import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by dorine.niel on 12/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringApplication.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    Commercial commercial, pierreDurand, rachidDurand, manuelPierre;

    @Before
    public void setUp(){
        //Delete all data from Employee table
        employeRepository.deleteAll();

        //Add test data
        commercial = new Commercial();
        commercial.setPrenom("Dorine");
        commercial.setNom("Niel");
        commercial = employeRepository.save(commercial);

        pierreDurand = new Commercial();
        pierreDurand.setPrenom("Pierre");
        pierreDurand.setNom("Durand");
        pierreDurand = employeRepository.save(pierreDurand);

        rachidDurand = new Commercial();
        rachidDurand.setPrenom("Rachid");
        rachidDurand.setNom("Durand");
        rachidDurand = employeRepository.save(rachidDurand);

        manuelPierre = new Commercial();
        manuelPierre.setPrenom("Manuel");
        manuelPierre.setNom("Pierre");
        manuelPierre = employeRepository.save(manuelPierre);
    }

    @Test
    public void testEmployeeIsNotFound() {
        //GIVEN

        //WHEN
        List<Employe> employeList = employeRepository.findByNomAndPrenom("TOTO", "TATA");

        //THEN
        Assertions.assertThat(employeList).isEmpty();
    }

    @Test
    public void testEmployeeNameisNotEmpty() {
        //GIVEN

        //WHEN
        List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Dorine");

        //THEN
        Assertions.assertThat(employeList).isNotEmpty();
        Assertions.assertThat(employeList).hasSize(1);
        Assertions.assertThat(employeList).contains(commercial);

    }

    @Test
    public void testEmployeeFirstNameisEmpty() {
        //GIVEN

        //WHEN
        List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Niel");

        //THEN
        Assertions.assertThat(employeList).isNotEmpty();
        Assertions.assertThat(employeList).hasSize(1);
        Assertions.assertThat(employeList).contains(commercial);
    }

    @Test
    public void testEmployeeWithNameAndFirstname() {
        //GIVEN

        //WHEN
        List<Employe> employeList = employeRepository.findByNomAndPrenom("Niel", "Dorine");

        //THEN
        Assertions.assertThat(employeList).isNotEmpty();
        Assertions.assertThat(employeList).hasSize(1);
        Assertions.assertThat(employeList).contains(commercial);
    }

    @Test
    public void testEmployeeWithSameName() {
        //GIVEN

        //WHEN
        List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("Pierre");

        //THEN
        Assertions.assertThat(employeList).isNotEmpty();
        Assertions.assertThat(employeList).hasSize(2);
        Assertions.assertThat(employeList).contains(pierreDurand, manuelPierre);

    }

}

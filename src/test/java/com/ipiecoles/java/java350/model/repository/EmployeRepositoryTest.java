package com.ipiecoles.java.java350.model.repository;

import com.ipiecoles.java.java350.MySpringApplication;
import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by HCHARBONNEYR on 12/03/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = MySpringApplication.class)
public class
EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Test
    public void testfindByNomOrPrenomAllIgnoreCasePrenom(){
        //given
        Commercial c = new Commercial();
        c.setPrenom("Pierre");
        c.setNom("Durand");
        c = employeRepository.save(c);
        //when
        List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");
        //then
        Assertions.assertThat(employeList).isEmpty();
        Assertions.assertThat(employeList).hasSize(1);
        Assertions.assertThat(employeList).contains(c);
    }
    @Test

    public void testFindByNomOrPrenomAllIgnoreCaseNom(){
        //given
        Commercial c = new Commercial();
        c.setPrenom("Pierre");
        c.setNom("Durand");
        c = employeRepository.save(c);
        //when
        List<Employe> employeList = employeRepository.findByNomOrPrenomAllIgnoreCase("durand");
        //then
        Assertions.assertThat(employeList).isEmpty();
        Assertions.assertThat(employeList).hasSize(1);
        Assertions.assertThat(employeList).contains(c);
    }
}

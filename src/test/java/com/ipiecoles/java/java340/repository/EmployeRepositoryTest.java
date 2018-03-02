package com.ipiecoles.java.java340.repository;

import java.util.List;

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

import com.ipiecoles.java.java340.SpringWebApplication;
import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;
import com.ipiecoles.java.java340.model.Technicien;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = SpringWebApplication.class)
public class EmployeRepositoryTest {

	@Autowired
    EmployeRepository employeRepository;
	
	Commercial pierreDurand, jeanJacques, jacquesDupond, mrRiche, mrRiche2, mrPauvre;
	
    @Before
    public void setUp() throws EmployeException {
        employeRepository.deleteAll();
        pierreDurand = new Commercial("Durand", "Pierre", "C12345", new LocalDate(), 100d, 0d,0);
        jeanJacques = new Commercial("Jean-Jacques", "Jean", "C12346", new LocalDate(), 100d, 0d,0);
        jacquesDupond = new Commercial("Dupond", "Jean-Jacques", "C12347", new LocalDate(), 100d, 0d,0);

        pierreDurand = employeRepository.save(pierreDurand);
        jeanJacques = employeRepository.save(jeanJacques);
        jacquesDupond = employeRepository.save(jacquesDupond);
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
    public void findEmployePlusRichesWith100d(){
        //Given
    	mrRiche = new Commercial("MONSIEUR", "Richard", "C13999", new LocalDate(), 100d, 0d,0);
    	mrPauvre = new Commercial("MONSIEUR", "Paul", "C13999", new LocalDate(), 100d, 0d,0);
        
        mrRiche = employeRepository.save(mrRiche);
        mrPauvre = employeRepository.save(mrPauvre);

        //When
        List<Employe> employesRiches = employeRepository.findEmployePlusRiches();

        //Then
        Assertions.assertThat(employesRiches).isEmpty();
    }
    
    @Test
    public void findEmployePlusRichesWith4500d(){
        //Given
    	mrRiche = new Commercial("MONSIEUR", "Richard", "C13999", new LocalDate(), 4500d, 0d,0);
    	mrRiche2 = new Commercial("MONSIEUR2", "Richard2", "C13999", new LocalDate(), 4600d, 0d,0);
    	mrPauvre = new Commercial("MONSIEUR", "Paul", "C13999", new LocalDate(), 100d, 0d,0);
        
        mrRiche = employeRepository.save(mrRiche);
        mrRiche2 = employeRepository.save(mrRiche2);
        mrPauvre = employeRepository.save(mrPauvre);

        //When
        List<Employe> employesRiches = employeRepository.findEmployePlusRiches();
        
        double moy = 0;
        double compt = 0;
        for (Employe empr : employeRepository.findAll()) {
			compt += 1;
			moy += empr.getSalaire();
		}
        moy /= compt;

        //Then       
        Assertions.assertThat(moy).isEqualTo(1583.3333333333333);
        
        Assertions.assertThat(employesRiches).isNotNull();
        Assertions.assertThat(employesRiches).hasSize(2);
        Assertions.assertThat(employesRiches).contains(mrRiche, mrRiche2);
        for (Employe emp : employesRiches) {
			Assertions.assertThat(emp.getSalaire()).isGreaterThan(moy);
		}
        Assertions.assertThat(employesRiches.iterator().next().getSalaire()).isEqualTo(4500d);
    }
   
}


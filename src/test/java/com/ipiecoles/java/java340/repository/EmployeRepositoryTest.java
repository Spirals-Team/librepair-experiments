package com.ipiecoles.java.java340.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.ipiecoles.java.java340.SpringWebApplication;
import com.ipiecoles.java.java340.model.Commercial;
import com.ipiecoles.java.java340.model.Employe;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(classes = SpringWebApplication.class)


public class EmployeRepositoryTest {
	 @Autowired
	    EmployeRepository employeRepository;
	 Commercial pierreDurand, jeanJacques, jacquesDurand;
	 @Before
	 public void setUp()
	 {
		 employeRepository.deleteAll(); 	//supprime tout
		 
	 }
	 
	  @Test
	    public void testfindByNomOrPrenomAllIgnoreCasePrenom()
	  {
		  	Commercial pierreDurand = new Commercial("Durand", "Pierre", "C12345", new LocalDate(), 1500d, 0d, 0);
			 Commercial jeanJacques = new Commercial("Jacques", "Jean", "C12335", new LocalDate(), 1600d, 0d, 0);
			 Commercial jacquesDurand = new Commercial("Durand", "Jacques", "C12845", new LocalDate(), 1800d, 0d, 0);
			 
			 
			 pierreDurand = employeRepository.save(pierreDurand);
			 jeanJacques = employeRepository.save(jeanJacques);
			 jacquesDurand = employeRepository.save(jacquesDurand);
	        //Given
	        //When 	
	        List<Employe> employes = employeRepository.findByNomOrPrenomAllIgnoreCase("pierre");
	        //Then
	        Assertions.assertThat(employes).hasSize(1); 
	        //Pour être sûr que ça ne renvoie qu'un seul employé
	        Assertions.assertThat(employes).contains(pierreDurand);
	    }
	  
	  //Pour se simplifier la vie on utilise les builder.
	 
	  @Test
	  	public void testFindEmployePlusRicheSalairseIdem()
	  	{
		  //Si tous les salaires sont identiques
		  Commercial pierreDurand = new Commercial("Durand", "Pierre", "C12345", new LocalDate(), 2000d, 0d, 0);
		  Commercial jeanJacques = new Commercial("Jacques", "Jean", "C12335", new LocalDate(), 2000d, 0d, 0);
		  Commercial jacquesDurand = new Commercial("Durand", "Jacques", "C12845", new LocalDate(), 2000d, 0d, 0);
			 
		  
		//On affirme qu'il n'y a pas de salarié avec un plus gros salaire, tous ont le même
		  List<Employe> employes = employeRepository.findEmployePlusRiches();
		employeRepository.save(pierreDurand);
		employeRepository.save(jeanJacques);
		employeRepository.save(jacquesDurand);
		
		Assertions.assertThat(employes).isEmpty();
		//Aucun salarié ne se distingue
		
	  	}
}

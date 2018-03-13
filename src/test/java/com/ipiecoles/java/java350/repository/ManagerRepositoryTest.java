package com.ipiecoles.java.java350.repository;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Manager;
import com.ipiecoles.java.java350.model.Technicien;
import com.ipiecoles.java.java350.service.ManagerService;

import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerRepositoryTest {

	@Autowired
	ManagerService managerService;
	@Autowired
	ManagerRepository managerRepository;
	@Autowired
	TechnicienRepository technicienRepository;
	
	@Before
	public void setup(){
		technicienRepository.deleteAll();
		managerRepository.deleteAll();
	}
	
	@Test
	public void testAddTechnicien(){
		//GIVEN
		Manager manager = new Manager("DURAND", "mathieu", "M12345", null, 2000d, null);
		manager = managerRepository.save(manager);
		
		Technicien technicien = new Technicien("dupuis", "mathieu", "T12345", null, 1000d, 2);
		technicien = technicienRepository.save(technicien);
		
		//WHEN
		managerService.addTechniciens(manager.getId(), technicien.getMatricule());
		
		Manager finalManager = managerRepository.findOneWithEquipeById(manager.getId());
		Technicien finalTechnicien = technicienRepository.findOne(technicien.getId());
		
		Assertions.assertThat(finalManager.getEquipe()).contains(finalTechnicien);
		Assertions.assertThat(finalTechnicien.getManager()).isNotNull();
		Assertions.assertThat(finalTechnicien.getManager()).isEqualTo(finalManager);
		
	}
	
	@Test
	public void testAddTechnicien_noManager(){
		//GIVEN
		
		Technicien technicien = new Technicien("dupuis", "mathieu", "T12345", null, 1000d, 2);
		technicien = technicienRepository.save(technicien);
		
		//WHEN
		
		try {
			managerService.addTechniciens(0L, technicien.getMatricule());
			Assertions.fail("ca aurait du planter");
		}
		catch(Exception e){
			//THEN
			Assertions.assertThat(e).isInstanceOf(EntityNotFoundException.class);
		}

	}
	
	@Test
	public void testAddTechnicien_noMatricule(){
		//GIVEN
		Manager manager = new Manager("DURAND", "mathieu", "M12345", null, 2000d, null);
		manager = managerRepository.save(manager);
		
		
		//WHEN
		
		try {
			managerService.addTechniciens(manager.getId(), "XXXX");
			Assertions.fail("ca aurait du planter");
		}
		catch(Exception e){
			//THEN
			Assertions.assertThat(e).isInstanceOf(EntityNotFoundException.class);
		}

	}
	
	@Test
	public void testAddTechnicien_withAnotherManager(){
		//GIVEN
		Manager manager = new Manager("DURAND", "mathieu", "M12345", null, 2000d, null);
		manager = managerRepository.save(manager);
		
		Manager manager2 = new Manager("DURAND", "mathieu", "R12345", null, 2000d, null);
		manager = managerRepository.save(manager2);
		
		Technicien technicien = new Technicien("dupuis", "mathieu", "T12345", null, 1000d, 2);
		technicien = technicienRepository.save(technicien);
		
		//WHEN
		managerService.addTechniciens(manager2.getId(), technicien.getMatricule());
		
		
		try {
			managerService.addTechniciens(manager.getId(), technicien.getMatricule());
			Assertions.fail("ca aurait du planter");
		}
		catch(Exception e){
			//THEN
			Assertions.assertThat(e).isInstanceOf(IllegalArgumentException.class);
		}
		
	}
	
}

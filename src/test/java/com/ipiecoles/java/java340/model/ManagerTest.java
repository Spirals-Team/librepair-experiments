package com.ipiecoles.java.java340.model;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ManagerTest {
	
	//(salaire * Entreprise.INDICE_MANAGER + (salaire * (double)equipe.size() / 10))
	//(1000*1.3+(1000*1/10))
	
	@Test
	public void testsetSalaireWith1000() {
		//Given
		Manager mng = new Manager();
		Technicien tec = new Technicien();
		mng.ajoutTechnicienEquipe(tec);
		mng.setSalaire(1000d);
		
		//When
		Double salaire = mng.getSalaire();
		Integer sizeEquipe = mng.getEquipe().size();
		
		//Then
		Assertions.assertThat(sizeEquipe).isEqualTo(1);
		Assertions.assertThat(salaire).isEqualTo(1400d);
	}
	
	//1480.27 * (1 + 10%)
	
	@Test
	public void testaugmenterSalaireWith10p() {
		//Given
		Manager mng = new Manager();
		Technicien tec = new Technicien();
		mng.ajoutTechnicienEquipe(tec);
		mng.augmenterSalaire(10/100d);
		
		//When
		Double salaireMng = mng.getSalaire();
		Set<Technicien> equipeMng = mng.getEquipe();
		Integer sizeEquipe = mng.getEquipe().size();
		
		//Then
		Assertions.assertThat(sizeEquipe).isEqualTo(1);
		Assertions.assertThat(salaireMng).isEqualTo(1628.297);
		for (Technicien technicien : equipeMng) {
			Assertions.assertThat(technicien.getSalaire()).isEqualTo(1628.297);
		}
	}
	
	
}

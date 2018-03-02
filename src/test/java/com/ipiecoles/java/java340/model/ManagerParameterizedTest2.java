package com.ipiecoles.java.java340.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class ManagerParameterizedTest2 {
	
		@Parameterized.Parameter(value = 0)
		  public Double pourçentage;
		
		@Parameterized.Parameter(value = 1)
		  public Double expectedNewSalaire;
		
		@Parameterized.Parameters(name = "Avec {0} % en plus son salaire est de : {1}")
		  public static Collection<Object[]> data() {
		      return Arrays.asList(new Object[][]{
		    	  {10/100d, 1628.297},
		    	  {75/100d, 2590.4725}
		      });
		  }
		
		//1480.27 * (1 + 10%) 296.054
		
		@Test
		public void testaugmenterSalaireWithParam() {
			//Given
			Manager mng = new Manager();
			Technicien tec = new Technicien();
			mng.ajoutTechnicienEquipe(tec);
			mng.augmenterSalaire(pourçentage);

			//When
			Double salaireMng = mng.getSalaire();
			Set<Technicien> equipeMng = mng.getEquipe();
			
			//Then
			Assertions.assertThat(salaireMng).isEqualTo(expectedNewSalaire);
			Assertions.assertThat(mng.getEquipe().iterator().next()).isEqualTo(tec);
			for (Technicien technicien : equipeMng) {
				Assertions.assertThat(technicien.getSalaire()).isEqualTo(expectedNewSalaire);
			}
		}
	

}

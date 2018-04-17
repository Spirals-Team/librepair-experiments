package com.ipiecoles.java.java340.model;

import java.util.HashSet;
import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;

public class ManagerTest {
	
	
	@Test
	public void getPrimeAnnuelleTest() {
			//Given
			Technicien theTechnicien = new Technicien("the", "Technicien", "T18911",new LocalDate(), 1500d, 0);
		
			Manager theManager =  new Manager("the", "Manager", "M52428", new LocalDate(), 2000d, new HashSet<>());
			
			//When
			HashSet<Technicien> equipe = new HashSet<Technicien>() {{
			    add(theTechnicien);
			}};
			theManager.setEquipe(equipe);
			//Then
			Assertions.assertThat(theManager.getPrimeAnnuelle()).isEqualTo(1259d);
			
		}
}

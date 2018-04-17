package com.ipiecoles.java.java340.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ManagerParameterizedTest {
	
	public static Technicien theTechnicien = new Technicien("the", "Technicien", "M12345", LocalDate.now(), 1000d, null);
	
	public Double technicianBaseSalary = 1000d;
	
	@Parameter(value = 0)
	public HashSet<Technicien> equipe;
	
	@Parameter(value = 1)
	public Double expectedPrime;
	
	static HashSet<Technicien> equipe1 = new HashSet<Technicien>() {
		 
		private static final long serialVersionUID = 1L;

	{
	    add(theTechnicien);
	}};
	
	@Parameters
	public static Collection<Object[]> data() {
	    return Arrays.asList(new Object[][]{
	    // equipe / prime 
	      {equipe1, 1600d}
	    });
	}
	
	@Test
	public void testgetPrimeAnnuelle() {
		
		//Given
	    Manager manager = new Manager("the", "Manager", "M82898", new LocalDate(), 2000d, new HashSet<>());
	    
	    //When
	    manager.setEquipe(equipe);
	
	
	    //Then
	    Assertions.assertThat(manager.getPrimeAnnuelle()).isEqualTo(expectedPrime);

	}
}
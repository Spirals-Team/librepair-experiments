package com.ipiecoles.java.java350.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ManagerParameterizedTest {

	@Parameter(value = 0)
	public Double salaireTest;
	
	@Parameter(value = 1)
	public Set<Technicien> equipeTest;
	
	@Parameter(value = 2)
	public Double expectedSalaire;
	
	@Parameters (name = "salaire {0} / expectedSalaire {2} : equipe {1}")
	public static Collection<Object[]> data() {
		
		//GIVEN
		Set<Technicien> equipeFull = new HashSet<>();
		equipeFull.add(new Technicien("1", null, null, null, 0d, null));
		equipeFull.add(new Technicien("2", null, null, null, 0d, null));
		equipeFull.add(new Technicien("3", null, null, null, 0d, null));
		equipeFull.add(new Technicien("4", null, null, null, 0d, null));
		
		Set<Technicien> equipeEmpty = new HashSet<>();
		
	    return Arrays.asList(new Object[][]{
	           { 1000d, equipeFull, 1700d},
	           { 1000d, equipeEmpty, 1300d},
	           { 0d, equipeFull, 0d},
	           { 0d, equipeEmpty, 0d}
	    	}
	        );
	}
	
	@Test
	public void test_ManagerSetSalaire(){
		//....given
		Manager manager = new Manager();
		manager.setEquipe(equipeTest);
		
		//WHEN
		manager.setSalaire(salaireTest);
		
		Double salaire = manager.getSalaire();
		
		//THEN
		System.out.println(salaire);
		Assertions.assertThat(salaire).isEqualTo(expectedSalaire);
	}
	
	
}

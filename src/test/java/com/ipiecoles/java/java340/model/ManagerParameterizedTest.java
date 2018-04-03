package com.ipiecoles.java.java340.model;
import java.util.Arrays;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ManagerParameterizedTest
{
	
	@Parameter(value = 0)
	public Double augmentation;
	
	@Parameter(value = 1)
	public Double augmentationResultat;
	
	@Parameters(name = "augmentation {0} est valide {1}")
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]
		{
			{0d, 2000d},	//Pas d'augmentation
			{0.1d, 2200d},	//+ 10%
			{1d, 4000d}		//On double
		});
	}
	
	
	@Test
	public void testAugmenterSalaire()
	{
		/*
		public void augmenterSalaire(Double pourcentage)
		{
		this.salaire = this.getSalaire() * (1 + pourcentage);
		}
		// Dans ce test : 2000 * (1 + 0)	/2000
		 * 				  2000 * (1 + 0.1)	/2200
		 *  			  2000 * (1 + 1)	/4000
		 */
		
		
		//Given
		Double salaire = 2000d;
		Manager manager = new Manager();
		//Modif salaire
		manager.setSalaire(salaire);
		manager.augmenterSalaire(augmentation);
		//When
		manager.getSalaire();
		//Then
		Assertions.assertThat(manager.getSalaire()).isEqualTo(augmentationResultat);
	}

}

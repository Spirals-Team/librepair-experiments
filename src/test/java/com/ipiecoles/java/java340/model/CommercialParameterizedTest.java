package com.ipiecoles.java.java340.model;

import java.util.Arrays;
import java.util.Collection;

import org.assertj.core.api.Assertions; //Biblio d'assertions mieux que junit donc à mettre aussi
import org.junit.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

	
	@RunWith(value = Parameterized.class)//org.junit.runners.Parameterized
	public class CommercialParameterizedTest
	{
	  @Parameter(value = 0)//org.junit.runners.Parameterized.Parameter
	  public Integer performance;
	  @Parameter(value = 1)
	  public Note quelleNote;
	  //org.junit.runners.Parameterized.Parameters
	  @Parameters(name = "performance {0} est valide : {1}")
	  public static Collection<Object[]> data()
	  {
	      return Arrays.asList(new Object[][]
	    		  {
	        {50, Note.INSUFFISANT}, {100, Note.PASSABLE}, {150, Note.BIEN}, {200, Note.TRES_BIEN}, {null, null}, {600, null}
	      });
	  }
	@Test
		public void testEquivalenceNote( )
		{
			//Given
			Commercial commercial = new Commercial();
			commercial.setPerformance(performance);
			//When
			Note note = commercial.equivalenceNote();
			//Then
			Assertions.assertThat(note).isEqualTo(quelleNote);
		}
	}
	


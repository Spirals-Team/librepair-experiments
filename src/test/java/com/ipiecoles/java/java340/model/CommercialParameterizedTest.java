package com.ipiecoles.java.java340.model;

import java.util.Arrays;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value= Parameterized.class)
public class CommercialParameterizedTest {
	
	@Parameter(value = 0)
	public Integer performance;
	
	@Parameter(value = 1)
	public Note note;
	
	@Parameters(name="Avec performance {0}, note = {1}")
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {
			{null,null}, {0,Note.INSUFFISANT}, {50,Note.INSUFFISANT}, {25, null}, {100,Note.PASSABLE}, 
			{150,Note.BIEN}, {200,Note.TRES_BIEN}
		});
	}
	
	@Test
	public void testEquivalence() {
		//GIVEN
		Commercial commercial = new Commercial();
		commercial.setPerformance(performance);
		
		// WHEN THEN
		if(performance == null) {
			Assertions.assertThat(commercial.equivalenceNote()).isNull();
		}
		else {
		Assertions.assertThat(commercial.equivalenceNote()).isEqualTo(note);
		}
	}
}
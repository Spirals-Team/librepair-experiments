package com.ipiecoles.java.java340.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

public class CommercialTest {
	
	@Test
	public void testGetPrimeAnnuelleWithCANull() {
		//Given
		Commercial commercial = new Commercial();
		commercial.setCaAnnuel(null);
		
		//When
		Double prime = commercial.getPrimeAnnuelle();
		
		//Then
		Assertions.assertThat(prime).isEqualTo(500d);
	}
	
}

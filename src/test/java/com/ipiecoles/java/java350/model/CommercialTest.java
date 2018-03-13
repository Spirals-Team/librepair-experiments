package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CommercialTest {

	@Test
	public void test_getPrimeAnnuellewithCANull (){
		//GIVEN
		Commercial commercial = new Commercial();
		commercial.setCaAnnuel(null);
		
		//WHEN
		Double prime = commercial.getPrimeAnnuelle();
		
		//THEN
		Assertions.assertThat(prime).isEqualTo(500d);
	}
	
	@Test
	public void test_getPrimeAnnuellewithCA0 (){
		//GIVEN
		Commercial commercial = new Commercial();
		commercial.setCaAnnuel(0d);
		
		//WHEN
		Double prime = commercial.getPrimeAnnuelle();
		
		//THEN
		Assertions.assertThat(prime).isEqualTo(500d);
	}
	
	@Test
	public void test_getPrimeAnnuellewithCA50000 (){
		//GIVEN
		Commercial commercial = new Commercial();
		commercial.setCaAnnuel(10000d);
		
		//WHEN
		Double prime = commercial.getPrimeAnnuelle();
		
		//THEN
		Assertions.assertThat(prime).isEqualTo(500d);
	}
}

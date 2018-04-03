//package com.ipiecoles.java.java340.model;
//
////Pour afficher dans le fichier "source"(dans ce cas commercial.java) les cas traités par les tests : clic droit,coverage As, JUnit test.
//
//import org.assertj.core.api.Assertions; //Biblio d'assertions mieux que junit donc à mettre aussi
//import org.junit.Test;
//
//
//public class CommercialTest {
//	@Test
//	//Les test sont tjs en class void
//	public void testGetPrimeAnnuelleWithCANull(){
//		//Given
//		Commercial commercial = new Commercial();
//		commercial.setCaAnnuel(null);
//		
//		//When
//		Double prime = commercial.getPrimeAnnuelle();
//		
//		//Then
//		Assertions.assertThat(prime).isEqualTo(500d);	
//	}
//	
//	@Test
//	//Les test sont tjs en class void
//	public void testGetMinimumPrimeAnnuelleSup(){
//		//Given
//		Commercial commercial = new Commercial();
//		commercial.setCaAnnuel(100000d);
//		
//		//When
//		Double prime = commercial.getPrimeAnnuelle();
//		
//		//Then
//		Assertions.assertThat(prime).isEqualTo(500d);	
//	}
//	
//	@Test
//	//Les test sont tjs en class void
//	public void testGetMinimumPrimeAnnuelleInf(){
//		//Given
//		Commercial commercial = new Commercial();
//		commercial.setCaAnnuel(9000d);
//		
//		//When
//		Double prime = commercial.getPrimeAnnuelle();
//		
//		//Then
//		Assertions.assertThat(prime).isEqualTo(500d);	
//	}
//	
//	@Test
//	//Les test sont tjs en class void
//	public void testGetMinimumPrimeAnnuelleWithCA0(){
//		//Given
//		Commercial commercial = new Commercial();
//		commercial.setCaAnnuel(0d);
//		
//		//When
//		Double prime = commercial.getPrimeAnnuelle();
//		
//		//Then
//		Assertions.assertThat(prime).isEqualTo(500d);
//		
//		
//	}
//}

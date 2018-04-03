package com.ipiecoles.java.java340.model;
import java.util.Arrays;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.ipiecoles.java.java340.exception.EmployeException;

@RunWith(value = Parameterized.class)
public class CommercialAnneeParameterizedTest
{
		Commercial jacquesDurand = new Commercial("Durand", "jacques", "C12353", new LocalDate(), 1600d, 60000d);
		public static LocalDate now = LocalDate.now();
		public static LocalDate yesterday = now.minusDays(1);
		public static LocalDate tomorrow = now.plusDays(1);
		@Parameter(value = 0)
		LocalDate dateEmbaucheIn;
		@Parameter(value = 1)
		LocalDate dateEmbaucheExpected;
		
		@Parameterized.Parameters(name = "dateEmbauche {0) set {1}")
		public static Collection<Object[]> data()
		{
			return Arrays.asList(new Object[][]
			{
				{now, now}, {yesterday, yesterday}, {tomorrow, null}
			});
		}
		
		public void testSetDateEmbauche() throws EmployeException {
	
			//Given
					Employe commercial = null;
					
					//When
		try
		{
					commercial.setDateEmbauche(dateEmbaucheIn);
					//Then
					Assertions.assertThat(commercial.getDateEmbauche()).isEqualTo(dateEmbaucheIn);
					Assertions.assertThat(dateEmbaucheExpected).isNotNull();
		} catch (EmployeException e)
		{
			Assertions.assertThat(e.getMessage()).isEqualTo("La date d'embauche ne peut être posterieure à la date courante");
			Assertions.assertThat(dateEmbaucheExpected).isNull();
		}finally
	{
		Assertions.assertThat(commercial.getDateEmbauche()).isEqualTo(dateEmbaucheExpected);		
		}
		}
}

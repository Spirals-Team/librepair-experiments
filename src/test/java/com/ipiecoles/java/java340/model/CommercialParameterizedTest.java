package com.ipiecoles.java.java340.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class CommercialParameterizedTest {
	
	  @Parameterized.Parameter(value = 0)
	  public Integer perf;
	  @Parameterized.Parameter(value = 1)
	  public Note expectedNote;
	  
	  @Parameterized.Parameters(name = "perf {0} à une note de : {1}")
	  public static Collection<Object[]> data() {
	      return Arrays.asList(new Object[][]{
	    	  {0, Note.INSUFFISANT},
              {50, Note.INSUFFISANT},
              {100, Note.PASSABLE},
              {150, Note.BIEN},
              {200, Note.TRES_BIEN},
              {null, null},
              {600, null}
	      });
	  }

	  @Test
	  public void testequivalenceNote()
	  {
	      //Given, When, Then
		  Commercial commercial = new Commercial();
		  commercial.setPerformance(perf);
		  
		  Note note = commercial.equivalenceNote();
		  
		  Assertions.assertThat(note).isEqualTo(expectedNote);
	  }
	  
}

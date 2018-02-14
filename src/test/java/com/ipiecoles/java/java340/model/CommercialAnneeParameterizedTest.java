package com.ipiecoles.java.java340.model;

import com.ipiecoles.java.java340.exception.EmployeException;
import com.ipiecoles.java.java340.model.maker.CommercialMaker;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.joda.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class CommercialAnneeParameterizedTest {

    public static LocalDate now = LocalDate.now();
    public static LocalDate yesterday = now.minusDays(1);
    public static LocalDate tomorrow = now.plusDays(1);


    @Parameterized.Parameter(value =0)
    public LocalDate dateEmbaucheIn;

    @Parameterized.Parameter(value=1)
    public LocalDate dateEmbaucheExpected;

    @Parameterized.Parameters(name = "dateEmbaucheIn {0} set {1}")
    public static Collection<Object[]>data(){
        return Arrays.asList(new Object[][]{
            {now,now},{yesterday,yesterday},{tomorrow,null}
        });
    }

    @Test
    public void testSetDateEmbauche()throws EmployeException{
        //Given
        Employe commercial = CommercialMaker.aCommercial().build();

        //When
        try {
            commercial.setDateEmbauche(dateEmbaucheIn);
            //Then
            Assertions.assertThat(commercial.getDateEmbauche()).isEqualTo(dateEmbaucheExpected);
        }catch (EmployeException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("La date d'embauche ne peut être " +
                    "postérieure à la date courante");
            Assertions.assertThat(dateEmbaucheExpected).isNull();
        } finally {
            //Assertions.assertThat(commercial.getDateEmbauche()).isEqualTo(dateEmbaucheExpected);
        }



    }
}

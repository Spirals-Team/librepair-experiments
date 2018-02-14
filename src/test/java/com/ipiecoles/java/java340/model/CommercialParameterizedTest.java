package com.ipiecoles.java.java340.model;

import org.assertj.core.api.Assertions;
import org.hibernate.annotations.Parameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class CommercialParameterizedTest {

    @Parameterized.Parameter(value = 0)
    public Integer performance;

    @Parameterized.Parameter(value = 1)
    public Note expectednote;

    @Parameterized.Parameters(name = "Performance: {0} équivaut à {1}")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {0,Note.INSUFFISANT},
                {50,Note.INSUFFISANT},
                {100,Note.PASSABLE},
                {150,Note.BIEN},
                {200, Note.TRES_BIEN},
                {null,null},
                {600,null}
        });
    }
    @Test
    public void equivalenceNote(){
        //Given
        Commercial commercial = new Commercial();
        commercial.setPerformance(performance);

        //When
        Note note = commercial.equivalenceNote();

        //Then
        Assertions.assertThat(note).isEqualTo(expectednote);
    }

//Aller voir JUnitparams

}

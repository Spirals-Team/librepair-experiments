package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by dorine.niel on 12/03/2018.
 */

@RunWith(value = Parameterized.class)
public class CommercialParameterizedTest{

    @Parameterized.Parameter (value = 0)
    public int performance;

    @Parameterized.Parameter (value = 1)
    public Note result;

    @Parameterized.Parameters (name = "performance {0} équivalent à {1}")
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] {
                {0, Note.INSUFFISANT},
                {50, Note.INSUFFISANT},
                {100, Note.PASSABLE},
                {150, Note.BIEN},
                {200, Note.TRES_BIEN},
                {600, null}
        });
    }

    @Test
    public void testEquivalenceNote() {
        //GIVEN
        Commercial commercial = new Commercial();
        commercial.setPerformance(performance);

        //WHEN
        Note note = commercial.equivalenceNote();

        //THEN
        Assertions.assertThat(note).isEqualTo(result);
    }

}


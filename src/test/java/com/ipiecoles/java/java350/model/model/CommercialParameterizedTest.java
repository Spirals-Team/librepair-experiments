package com.ipiecoles.java.java350.model.model;

import com.ipiecoles.java.java350.model.Commercial;
import com.ipiecoles.java.java350.model.Note;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by HCHARBONNEYR on 12/03/2018.
 */
    @RunWith(value = Parameterized.class)
    public class CommercialParameterizedTest {

        @Parameter(value = 0)
        public Integer perf;
        @Parameter(value = 1)
        public Note expectedNote;

        @Parameterized.Parameters(name = "perf {0} est équivalent à : {1}")
        public static Collection<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {0 , Note.INSUFFISANT},
                    {50, Note.INSUFFISANT},
                    {100, Note.PASSABLE},
                    {150, Note.BIEN},
                    {200, Note.TRES_BIEN},
                    {null,null},
                    {600,null},
            });
        }

    @Test
    public void testCheckCommercialNote(){
        //Given
        Commercial commercial = new Commercial();
        commercial.setPerformance(perf);

        //when
        Note note = commercial.equivalenceNote();

        //then
        Assertions.assertThat(note).isEqualTo(expectedNote);
    }
}

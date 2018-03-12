package com.ipiecoles.java.java350.model.model;


import com.ipiecoles.java.java350.model.Commercial;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by Dorine.Niel on 12/03/2018.
 */
@RunWith(JUnitParamsRunner.class)
public class CommercialTest {

    @Test
    @Parameters({
            //"null , 500d",
            "0d , 500d",
            "100000d , 5000d",
    })
    public void testPrimeAnnuelle(Double caAnnuel, Double expectedPrime){
        //Given initialisation des données d'entrée
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(caAnnuel);
        //When Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();
        //Then vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(expectedPrime);

    }

    @Test
    public void testPrimeAnnuelleWithCANull(){
        //Given initialisation des données d'entrée
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(null);
        //When Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();
        //Then vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(500d);

    }

    @Test
    public void testPrimeAnnuelleIsHigher(){
        //given
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(100000d);

        //when
        Double prime = commercial.getPrimeAnnuelle();

        //then
        Assertions.assertThat(prime).isEqualTo(5000d);
    }

    @Test
    public void testPrimeAnnuelleIsZero(){
        //Given initialisation des données d'entrée
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(0d);
        //When Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();
        //Then vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(500d);
    }

}
/**package com.ipiecoles.java.java350.model;

//import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


 * Created by dorine.niel on 12/03/2018.

public class CommercialTest {

    @Test
    @Parameterized.Parameters({
            //"null,500d",
            "0d,500d",
            "100000d,500d"
    })

    @Test
    public void testPrimeAnnuelle(Double caAnnuel, Double expectedPrime) {
        //GIVEN - Initialisation des données
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(caAnnuel);

        //WHEN - Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();

        //THEN - Vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(500d);
    }

    @Test
    public void testPrimeAnnuelleWithCANull() {
        //GIVEN - Initialisation des données
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(null);

        //WHEN - Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();

        //THEN - Vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(500d);
    }

    @Test
    public void testPrimeAnnuelleIsHigherThan500d() {
        //GIVEN - Initialisation des données
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(100000d);

        //WHEN - Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();

        //THEN - Vérification par rapport à la sortie de la méthode
        //Veillez à être contraignant dans la sortie de la méthode pour éviter les erreurs dans le code
        Assertions.assertThat(prime).isEqualTo(5000d);
    }

    @Test
    public void testPrimeAnnuelleIsBelowThan500d() {
        //GIVEN - Initialisation des données
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(50.50);

        //WHEN - Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();

        //THEN - Vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isEqualTo(500d);
    }

    @Test
    public void testPrimeAnnuelleIsPositive() {
        //GIVEN - Initialisation des données
        Commercial commercial = new Commercial();
        commercial.setCaAnnuel(50.50);

        //WHEN - Exécution/test de la méthode
        Double prime = commercial.getPrimeAnnuelle();

        //THEN - Vérification par rapport à la sortie de la méthode
        Assertions.assertThat(prime).isNotNegative();
    }

}*/


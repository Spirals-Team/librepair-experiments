package pl.sternik.ss.zadania.zad5;


import org.junit.Test;
import  static org.assertj.core.api.Assertions.assertThat;

public class Zad5Test{

   @Test
    public void dummyTest(){
        assertThat(1).isEqualTo(1);

    }

    @Test
    public void petlaFor() {
       String[] tablica = {"RAZ", "DWA", "TRZY", "CZTERY"};
       String excepted = "\nRAZ\nDWA\nTRZY\nCZTERY";
       String actual = Zad5.getStringOfArrayFor(tablica);
       assertThat(actual).isEqualTo(excepted);
    }

    @Test
    public void petleWhile() {
        String[] tablica = {"RAZ", "DWA", "TRZY", "CZTERY"};
        String excepted = "\nRAZ\nDWA\nTRZY\nCZTERY";
        String actual = Zad5.getStringOfArrayWhile(tablica);
        assertThat(actual).isEqualTo(excepted);
    }

    @Test
    public void zrobPetleForEach() {
        String[] tablica = {"RAZ", "DWA", "TRZY", "CZTERY"};
        String excepted = "\nRAZ\nDWA\nTRZY\nCZTERY";
        String actual = Zad5.getStringOfArrayForEach(tablica);
        assertThat(actual).isEqualTo(excepted);

    }
}
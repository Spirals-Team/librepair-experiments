package pl.sternik.ss.zadania.zad5;

public class Zad5 {

    public static void main(String[] args) {
        String[] tablica = {"1", "2", "3"};

        System.out.println(getStringOfArrayFor(tablica));
        System.out.println(getStringOfArrayForEach(tablica));
        System.out.println(getStringOfArrayWhile(tablica));

    }

    public static String getStringOfArrayFor(String[] tablica) {
        int i;
        String temp = "";
        for (i = 0; i <= tablica.length - 1; i++) {
            temp += tablica[i] + "\t";
        }
        return temp;
    }

    public static String getStringOfArrayForEach(String[] tablica) {
        String tmp = "";
        for (String value : tablica) {
            tmp += value + "\t";
        }
        return tmp;
    }

    public static String getStringOfArrayWhile(String[] tablica) {
        int i = 0;
        String temp = "";
        while (i <= tablica.length - 1) {
            temp += tablica[i] + "\t";
            i++;
        }
        return temp;
    }


}

package pl.sternik.ss.zadania.Sklep;

import java.util.Date;

public class Shop {

    static int[] topProducts = new int[10];

    private static Object[][] magazyn;

    Downloadable downloadable;

    public Downloadable getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Downloadable downloadable) {
        this.downloadable = downloadable;
    }

    public static int[] getTopProducts() {
        return topProducts;
    }

    public static void setTopProducts(int[] topProducts) {
        Shop.topProducts = topProducts;
    }

    public class Order{
        public int[] quantities;
        public Date data;

        public void accept(){
            for (int i = 0; i < magazyn.length; i++) {
                Integer jest = (Integer) magazyn[i][1];
                Integer nowe = quantities[i];
                Integer razem = jest + nowe;
                magazyn[i][1] = razem;
            }
        }

        public int[] getQuantities() {
            return quantities;
        }

        public void setQuantities(int[] quantities) {
            this.quantities = quantities;
        }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }
    }

    public static void ustawStanPoaczatkowy(){
        magazyn=new Object[5][2];
        for (int i = 0; i < 5; i++) {
            magazyn [i][0] = "przedmiot " + i + " stan: ";
            magazyn[i][1]=i+2;
        }
    }

    public static <T> void pokazStan(T[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
       ustawStanPoaczatkowy();
       Shop sklep = new Shop();
        pokazStan(magazyn);

        Shop.Order ord1 = sklep.new Order();
        ord1.data = new Date();
        int[] zam1 = { 10, 10, 10, 10, 10, 5, 0, 5, 0, 5 };
        ord1.quantities = zam1;
        ord1.accept();
        System.out.println("----- Stan po 1 zamówieniu");
        sklep.pokazStan(magazyn);

        Shop.Order ord2 = sklep.new Order();
        ord2.data = new Date();
        int[] zam2 = { 40, 30, 20, 4, 3, 2, 0, 1, 33, 15 };
        ord2.quantities = zam2;
        ord2.accept();
        System.out.println("----- Stan po 2 zamówieniu");
        sklep.pokazStan(magazyn);

        sklep.setDownloadable(new Downloadable() {
            @Override
            public String checkFileFormat() {
                return "png";
            }

            @Override
            public double checkFileSize() {
                return 42;
            }
        });
        System.out.println("---------->"+sklep.getDownloadable().checkFileSize());



    }

}

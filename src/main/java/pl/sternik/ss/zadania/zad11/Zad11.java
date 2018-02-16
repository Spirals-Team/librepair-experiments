package pl.sternik.ss.zadania.zad11;


public class Zad11 {
    public enum OrderState {NOWE, OCZEKUJACE, REALIZOWANE, WYSLANE, ZWROT}

    public static void main(String[] args) {
        for (int i = 0; i < OrderState.values().length; i++) {
            System.out.println(printOrderState(OrderState.values()[i]));
        }


    }

    public static String printOrderState(OrderState orderState) {

        switch (orderState) {
            case REALIZOWANE:
               return ("PILNE!");
            case ZWROT:
                return ("KONTAKT!");
            default:
                return (orderState.name());
        }
    }


}

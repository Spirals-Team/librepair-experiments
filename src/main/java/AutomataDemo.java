public class AutomataDemo {
    public static void main(String[] args) {
        Automata a = new Automata();
        float technicanMoney = 6.7f;
        // The technician "includes" a service machine
        a.on();
        // He sees before himself a menu
        // He wants to drink Cola, but not enough money, but it did not stop him
        a.coin(technicanMoney);
        // He chose the coveted drink, and understands that he still does not get anything
        try {
            a.choice("Coca-Cola");
        } catch (UnsupportedOperationException ex) {
            System.out.println(ex.getMessage());
        }
        a.cancel();
        // All the same, comrades helped him out and gave him some money
        // But he slightly forgot the assortment ...
        System.out.println(a.printMenu());
        // Finally, he will now get his drink!
        a.coin(technicanMoney * 2);
        a.choice("Coca-Cola");

        // Left just a little bit...
        System.out.println("You got a " + a.cook());

        // He realized that there was still money left and chose coffee
        a.choice("Coffee");
        System.out.println("You got a " + a.cook());
        a.off();
    }
}

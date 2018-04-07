package model;


public class AccountManager {

    public static void processPayment(double rentCost, User tenant, User owner){
        tenant.debitCredit(rentCost);
        owner.addCredit(rentCost);
    }
}

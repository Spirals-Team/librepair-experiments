package ru.job4j.control;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Bankir {
    public static void main(String[] args) {


        User alex = new User("Alex", "1234 1234");
        User sasha = new User("Sasha", "2345 2345");
        AccountBank alexAc1 = new AccountBank(10000, "Che");
        AccountBank alexAc2 = new AccountBank(25000, "Ekb");
        AccountBank sashaAc1 = new AccountBank(5000, "Emg");
        BankInc bank = new BankInc();
        bank.addUser(alex);
        bank.addUser(sasha);
        bank.addAccountToUser("1234 1234", alexAc1);
        bank.addAccountToUser("1234 1234", alexAc2);
        bank.addAccountToUser("2345 2345", sashaAc1);
        bank.transferMoney("1234 1234", "Che", "2345 2345", "Emg", 1000);
        System.out.println(alexAc1.value);
        System.out.println(sashaAc1.value);
    }
}
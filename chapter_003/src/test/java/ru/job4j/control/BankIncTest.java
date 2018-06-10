package ru.job4j.control;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class BankIncTest {
    @Test
    public void whenTransgerMoney() {

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
        double[] result = new double[] {alexAc1.value, sashaAc1.value};
        assertThat(result, is(new double[] {9000.0, 6000.0}));
    }
}


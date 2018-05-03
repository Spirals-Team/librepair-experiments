package ru.job4j.controlvopros;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    /**
     * проверим получим ли мы значение тру
     */
    @Test
    public void transferTesttrue() {
        Account destantion = new Account(65654654, "Дистанция чего то там");
        System.out.println(destantion);
        boolean res = destantion.transfer(destantion, 211);
        System.out.println(destantion);
        boolean expected = true;
        assertThat(expected, Is.is(res));
    }

    /**
     * проверим получим ли мы значение фалшь
     * обратили внимание что значение наших аккоунт не меняется при любом раскладе - не понятное что хотели сделать тим методом
     */
    @Test
    public void transfer() {
        Account destantion = new Account(65654654, "Дистанция чего то там");
        System.out.println(destantion);
        boolean res = destantion.transfer(destantion, 656546545);
        System.out.println(destantion);
        boolean expected = false;
        assertThat(expected, Is.is(res));
    }

}
package ru.job4j.coffe;

import org.junit.Test;
import ru.job4j.chess.FigureNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CoffeMachineTest {

    /**
     * Тест получения сдачи с 50
     * @throws NoMoneyException недостаточно денег
     */
    @Test
    public void getChangesFrom50() throws NoMoneyException {

        // стоимость кофе
        final int price = 32;
        // сумма, закинутая в автомат
        final int value = 50;
        // ожидаемый результат по сдачи
        final ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(10,5,2,1));

        CoffeMachine coffe=new CoffeMachine();

        assertThat(coffe.changes(value, price), is(result));

    }

    /**
     * Тест нелостатка денег
     * @throws NoMoneyException недостаточно денег
     */
    @Test(expected = NoMoneyException.class)
    public void getNoMoneyException() throws NoMoneyException {

        // стоимость кофе
        final int price = 32;
        // сумма, закинутая в автомат
        final int value = 20;
        // ожидаемый результат по сдачи
        final ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(10,5,2,1));

        CoffeMachine coffe=new CoffeMachine();

        coffe.changes(value, price);

    }

    /**
     * Тест без сдачи
     * @throws NoMoneyException недостаточно денег
     */
    @Test
    public void getNoChanges() throws NoMoneyException {

        // стоимость кофе
        final int price = 32;
        // сумма, закинутая в автомат
        final int value = 32;
        // ожидаемый результат по сдачи
        final ArrayList<Integer> result = new ArrayList<Integer>();

        CoffeMachine coffe=new CoffeMachine();

        assertThat(coffe.changes(value, price), is(result));

    }

}
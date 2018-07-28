package ru.job4j.burse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BurseTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private Burse burse;
    private Form form1;
    private Form form2;
    private Form form3;
    private Form form4;

    public void setUp() {
        burse = new Burse();
        burse.add(new Form("BRV", Action.ask, Type.add, 55.50, 20));
        burse.add(new Form("BRV", Action.ask, Type.add, 57.55, 15));
        burse.add(new Form("BRV", Action.bid, Type.add, 54.00, 10));
        burse.add(new Form("BRV", Action.bid, Type.add, 55.00, 15));
        form1 = new Form("ViG", Action.bid, Type.add, 90.00, 20);
        form2 = new Form("ViG", Action.bid, Type.add, 95.00, 10);
        form3 = new Form("ViG", Action.ask, Type.add, 98.00, 20);
        form4 = new Form("ViG", Action.ask, Type.add, 100.00, 10);
        burse.add(form1);
        burse.add(form2);
        burse.add(form3);
        burse.add(form4);

    }

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
        setUp();
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void showListOfEmmiterWhichHaveSomeFormInTheirGlasses() {
        burse.displayEmmiters();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("ViG")
                                .add("BRV").toString()
                )
        );
    }

    @Test
    public void showGlassOfSpecifiedEmmiter() {
        burse.displayGlass("BRV");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Продажа\tЦена\tПокупка")
                                .add("\t\t57.55\t15")
                                .add("\t\t55.50\t20")
                                .add("15\t\t55.00")
                                .add("10\t\t54.00").toString()
                )
        );
    }

    @Test
    public void whenThereAreNotEmmitent() {
        burse.add(new Form("ViG", Action.bid, Type.delete, 90.00, 20));
        burse.add(new Form("ViG", Action.bid, Type.delete, 95.00, 10));
        burse.add(new Form("ViG", Action.ask, Type.delete, 98.00, 20));
        burse.add(new Form("ViG", Action.ask, Type.delete, 100.00, 10));
        burse.displayGlass("ViG");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("На бирже нет заявок данного эммитента").toString()
                )
        );

    }

    @Test
    public void showListOfEmmiterWhichHaveOrHadAnyForms() {
        burse.add(new Form("ViG", Action.bid, Type.delete, 90.00, 20));
        burse.add(new Form("ViG", Action.bid, Type.delete, 95.00, 10));
        burse.add(new Form("ViG", Action.ask, Type.delete, 98.00, 20));
        burse.add(new Form("ViG", Action.ask, Type.delete, 100.00, 10));
        burse.displayHistoryEmmiters();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("ViG")
                                .add("BRV").toString()
                )
        );
    }

    @Test
    public void showListOfHistoryOfFormOfEmmiterWhoDoNotHaveAnyFormsRightNow() {
        Form form5 = new Form("ViG", Action.bid, Type.delete, 90.00, 20);
        Form form6 = new Form("ViG", Action.bid, Type.delete, 95.00, 10);
        Form form7 = new Form("ViG", Action.ask, Type.delete, 98.00, 20);
        Form form8 = new Form("ViG", Action.ask, Type.delete, 100.00, 10);
        burse.add(form5);
        burse.add(form6);
        burse.add(form7);
        burse.add(form8);
        burse.displayHistoryForm("ViG");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add(form1.toString())
                                .add(form2.toString())
                                .add(form3.toString())
                                .add(form4.toString())
                                .add(form5.toString())
                                .add(form6.toString())
                                .add(form7.toString())
                                .add(form8.toString()).toString()
                )
        );
    }

    @Test
    public void whenFormsHaveTheSamePriceThenTheirVolumesAreSummedAndThereIsOnlyOneFormInGlass() {
        burse.add(new Form("BRV", Action.ask, Type.add, 55.50, 1000));
        burse.add(new Form("BRV", Action.bid, Type.add, 54.00, 1000));
        burse.displayGlass("BRV");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Продажа\tЦена\tПокупка")
                                .add("\t\t57.55\t15")
                                .add("\t\t55.50\t1020") //смотреть тут
                                .add("15\t\t55.00")
                                .add("1010\t\t54.00").toString() //смотреть тут
                )
        );
    }

    @Test
    public void whenAddFormWithPriceWichIsTheSameThanPriceOfFormWichThereIsInGlassAndTheirTypesIsOppositeVolumesAreEqual() {
        burse.add(new Form("BRV", Action.bid, Type.add, 55.50, 20));
        burse.add(new Form("BRV", Action.ask, Type.add, 55.00, 15));
        burse.displayGlass("BRV");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Продажа\tЦена\tПокупка")
                                .add("\t\t57.55\t15")
                                .add("10\t\t54.00").toString()
                )
        );
    }

    @Test
    public void whenAddFormWithPriceWichIsTheSameThanPriceOfFormWichThereIsInGlassAndTheirTypesIsOppositeVolumesAreMore() {
        burse.add(new Form("BRV", Action.bid, Type.add, 55.50, 30));
        burse.add(new Form("BRV", Action.ask, Type.add, 55.50, 5));
        burse.displayGlass("BRV");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Продажа\tЦена\tПокупка")
                                .add("\t\t57.55\t15")
                                .add("5\t\t55.50") //дважды изменяется
                                .add("15\t\t55.00")
                                .add("10\t\t54.00").toString()
                )
        );
    }

    @Test
    public void whenAddFormWithPriceWichIsTheSameThanPriceOfFormWichThereIsInGlassAndTheirTypesIsOppositeVolumesAreLess() {
        burse.add(new Form("BRV", Action.bid, Type.add, 55.50, 1));
        burse.add(new Form("BRV", Action.ask, Type.add, 55.00, 1));
        burse.displayGlass("BRV");
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("Продажа\tЦена\tПокупка")
                                .add("\t\t57.55\t15")
                                .add("\t\t55.50\t19")
                                .add("14\t\t55.00")
                                .add("10\t\t54.00").toString()
                )
        );
    }
}

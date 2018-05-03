package ru.job4j.testovoezadanie;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class UtilitesTest {

    @Test
    public void sortedDataStrighttest() {
        Utilites utilites = new Utilites();
        Database database = new Database();
        database.addDeportamentList(Arrays.asList(
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        utilites.sortedDataStright(database);
        Database expected = new Database();
        expected.addDeportamentList(Arrays.asList(
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        assertThat(database, Is.is(expected));
    }

    @Test
    public void sortedDataRewerstest() {
        Utilites utilites = new Utilites();
        Database database = new Database();
        database.addDeportamentList(Arrays.asList(
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        utilites.sortedDataRewers(database);
        Database expected = new Database();
        expected.addDeportamentList(Arrays.asList(
                "K2",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        ));
        assertThat(database, Is.is(expected));
    }

    @Test
    public void refactorDatabasetest() {
        Utilites utilites = new Utilites();
        Database database = new Database();
        database.addDeportamentList(Arrays.asList(
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        utilites.refactorDatabase(database);
        Database expected = new Database();
        expected.addDeportamentList(Arrays.asList(
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2",
                "K2\\SK1\\SSK2"
        ));
        assertThat(database, Is.is(expected));
    }

    @Test
    public void fullDatabasesortedDataStrightest() {
        Utilites utilites = new Utilites();
        Database database = new Database();
        database.addDeportamentList(Arrays.asList(
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        utilites.refactorDatabase(database);
        utilites.sortedDataStright(database);
        Database expected = new Database();
        expected.addDeportamentList(Arrays.asList(
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        ));
        assertThat(database, Is.is(expected));
    }
    @Test
    public void fullDatabasesortedDataRewerstest() {
        Utilites utilites = new Utilites();
        Database database = new Database();
        database.addDeportamentList(Arrays.asList(
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"));
        utilites.refactorDatabase(database);
        utilites.sortedDataRewers(database);
        Database expected = new Database();
        expected.addDeportamentList(Arrays.asList(
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        ));
        assertThat(database, Is.is(expected));
    }
}
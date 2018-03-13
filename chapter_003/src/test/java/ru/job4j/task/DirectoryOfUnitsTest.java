package ru.job4j.task;

import org.junit.Test;
import ru.job4j.generic.User;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DirectoryOfUnitsTest {

    List<Unit> getListOfUnitsForTest() {
        List<Unit> unitList = new ArrayList<Unit>();
        unitList.addAll(Arrays.asList(
                new Unit("K1\\SK1"),
                new Unit("K1\\SK2"),
                new Unit("K2\\SK1"),
                new Unit("K1\\SK1\\SSK1"),
                new Unit("K1\\SK1\\SSK2"),
                new Unit("K2"),
                new Unit("K1"),
                new Unit("K2\\SK1\\SSK1"),
                new Unit("K2\\SK1\\SSK2")
        ));
        return unitList;
    }

    @Test
    public void whenSortDirectoriesByNameThenGetSortedDirectoriesByName() {

        List<Unit> unitList = this.getListOfUnitsForTest();

        DirectoryOfUnits directoryOfUnits = new DirectoryOfUnits();
        List<Unit> resultUnitsList = directoryOfUnits.sortUnitsDesc(unitList);

        List<Unit> expectedUnitList = new ArrayList<Unit>();
        expectedUnitList.addAll(Arrays.asList(
                new Unit("K1"),
                new Unit("K1\\SK1"),
                new Unit("K1\\SK1\\SSK1"),
                new Unit("K1\\SK1\\SSK2"),
                new Unit("K1\\SK2"),
                new Unit("K2"),
                new Unit("K2\\SK1"),
                new Unit("K2\\SK1\\SSK1"),
                new Unit("K2\\SK1\\SSK2")

        ));

        assertThat(resultUnitsList, is(expectedUnitList));
    }

    @Test
    public void whenSortDirectoriesByNameAscThenGetSortedDirectoriesByNameAsc() {

        List<Unit> unitList = this.getListOfUnitsForTest();

        DirectoryOfUnits directoryOfUnits = new DirectoryOfUnits();
        List<Unit> resultUnitsList = directoryOfUnits.sortUnitsAsc(unitList);

        List<Unit> expectedUnitList = new ArrayList<Unit>();

        expectedUnitList.addAll(Arrays.asList(
                new Unit("K2"),
                new Unit("K2\\SK1"),
                new Unit("K2\\SK1\\SSK2"),
                new Unit("K2\\SK1\\SSK1"),
                new Unit("K1"),
                new Unit("K1\\SK2"),
                new Unit("K1\\SK1"),
                new Unit("K1\\SK1\\SSK2"),
                new Unit("K1\\SK1\\SSK1")
        ));

        assertThat(resultUnitsList, is(expectedUnitList));
    }



}

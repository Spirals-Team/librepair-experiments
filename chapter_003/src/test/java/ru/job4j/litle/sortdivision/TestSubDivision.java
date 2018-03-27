package ru.job4j.litle.sortdivision;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 28.09.17;
 * @version $Id$
 * @since 0.1
 */
public class TestSubDivision {
    /**
     * Тест добавления недостающих элементов вышестоящей структуре.
     */
    @Test
    public void whelArrayRefactorThenArrayHasNewElements() {
        SortSubDivision sortSubDivision = new SortSubDivision();
        ArrayList<String> list = new ArrayList<>();
        list.add("k1/sk1/ssk1");
        list.add("k1/sk2/ssk1");
        list.add("k2/sk2/ssk4");
        List<String> result = sortSubDivision.refactorSubdivision(list);
        ArrayList<String> ex = new ArrayList<>();
        ex.add("k1/sk1/ssk1");
        ex.add("k1/sk2/ssk1");
        ex.add("k2/sk2/ssk4");
        ex.add("k1");
        ex.add("k1/sk1");
        ex.add("k1/sk2");
        ex.add("k2");
        ex.add("k2/sk2");
        assertThat(result.equals(ex), is(true));
        assertTrue(result.containsAll(ex));
        assertTrue(ex.containsAll(result));
    }

    /**
     *Тест сортировки по возрастанию.
     */
    @Test
    public void byIncrease() {
        SortSubDivision sortSubDivision = new SortSubDivision();
        ArrayList<String> list = new ArrayList<>();
        list.add("k1/sk1/ssk2");
        list.add("k1/sk1/ssk3");
        list.add("k1/sk1/ssk1");
        list.add("k1/sk2/ssk1");
        list.add("k2/sk1/ssk1");
        List<String> subdivisions = sortSubDivision.refactorSubdivision(list);
        List<String> sortedByIncrease = sortSubDivision.sortSubdivisionByIncrease(subdivisions);
        List<String> ex = new ArrayList<>();
        ex.add("k1");
        ex.add("k1/sk1");
        ex.add("k1/sk1/ssk1");
        ex.add("k1/sk1/ssk2");
        ex.add("k1/sk1/ssk3");
        ex.add("k1/sk2");
        ex.add("k1/sk2/ssk1");
        ex.add("k2");
        ex.add("k2/sk1");
        ex.add("k2/sk1/ssk1");
        assertThat(sortedByIncrease.equals(ex), is(true));
    }

    /**
     *Тест сортировки по убыванию.
     */
    @Test
    public void byWaning() {
        SortSubDivision sortSubDivision = new SortSubDivision();
        ArrayList<String> list = new ArrayList<>();
        list.add("k1/sk1/ssk2");
        list.add("k1/sk1/ssk3");
        list.add("k1/sk1/ssk1");
        list.add("k1/sk2/ssk1");
        list.add("k2/sk1/ssk1");
        List<String> subdivisions = sortSubDivision.refactorSubdivision(list);
        System.out.println(subdivisions);
        List<String> sortByWaning = sortSubDivision.sortSubdivisionByWaning(subdivisions);
        List<String> ex = new ArrayList<>();
        ex.add("k2");
        ex.add("k2/sk1");
        ex.add("k2/sk1/ssk1");
        ex.add("k1");
        ex.add("k1/sk2");
        ex.add("k1/sk2/ssk1");
        ex.add("k1/sk1");
        ex.add("k1/sk1/ssk3");
        ex.add("k1/sk1/ssk2");
        ex.add("k1/sk1/ssk1");
        assertThat(sortByWaning.equals(ex), is(true));
    }
}

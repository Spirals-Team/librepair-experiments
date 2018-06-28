package ru.job4j.collections;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SortDepartmentTest {

    SortDepartment department = new SortDepartment();

    @Before
    public void init() {
        department.add("K1\\SK1");
        department.add("K1\\SK2");
        department.add("K1\\SK1\\SSK1");
        department.add("K1\\SK1\\SSK2");
        department.add("K2");
        department.add("K2\\SK1\\SSK1");
        department.add("K2\\SK1\\SSK2");
    }

    @Test
    public void whenPollFromStackThenReturnLastElement() {
        String[] sor1 = new String[department.size()];
        int i = 0;
        for (String a : department.sortSet()) {
            sor1[i] = a;
            i++;
        }
        assertThat(sor1[0], is("K1"));
        assertThat(sor1[1], is("K1\\SK1"));
        assertThat(sor1[2], is("K1\\SK1\\SSK1"));
        assertThat(sor1[3], is("K1\\SK1\\SSK2"));
        assertThat(sor1[4], is("K1\\SK2"));
        assertThat(sor1[5], is("K2"));
        assertThat(sor1[6], is("K2\\SK1"));
        assertThat(sor1[7], is("K2\\SK1\\SSK1"));
        assertThat(sor1[8], is("K2\\SK1\\SSK2"));
    }

    @Test
    public void whenPollFromStackThenReturnLastElement2() {
        String[] sor1 = new String[department.size()];
        int i = 0;
        for (String a : department.sortSetByInc()) {
            sor1[i] = a;
            i++;
        }
        assertThat(sor1[0], is("K2"));
        assertThat(sor1[1], is("K2\\SK1"));
        assertThat(sor1[2], is("K2\\SK1\\SSK2"));
        assertThat(sor1[3], is("K2\\SK1\\SSK1"));
        assertThat(sor1[4], is("K1"));
        assertThat(sor1[5], is("K1\\SK2"));
        assertThat(sor1[6], is("K1\\SK1"));
        assertThat(sor1[7], is("K1\\SK1\\SSK2"));
        assertThat(sor1[8], is("K1\\SK1\\SSK1"));

    }
}
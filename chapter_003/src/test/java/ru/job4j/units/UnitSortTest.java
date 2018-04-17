package ru.job4j.units;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UnitSortTest {

    @Test
    public void whenSortAscending() {
        String[] array = {
                            "K1\\SK1",
                            "K1\\SK1\\SSK1",
                            "K1\\SK1\\SSK2",
                            "K1\\SK2",
                            "K2\\SK1",
                            "K2\\SK1\\SSK1",
                            "K2\\SK1\\SSK2"
        };
        String[] newArray = new UserSort().addUnit(array);
        Arrays.sort(newArray, new AscendingSort());
        assertThat(newArray, is(new String[] {
                                                "K1",
                                                "K1\\SK1",
                                                "K1\\SK1\\SSK1",
                                                "K1\\SK1\\SSK2",
                                                "K1\\SK2",
                                                "K2",
                                                "K2\\SK1",
                                                "K2\\SK1\\SSK1",
                                                "K2\\SK1\\SSK2"})
        );
    }

    @Test
    public void whenSortDescending() {
        String[] array = {
                            "K1\\SK1",
                            "K1\\SK1\\SSK1",
                            "K1\\SK1\\SSK2",
                            "K1\\SK2",
                            "K2\\SK1",
                            "K2\\SK1\\SSK1",
                            "K2\\SK1\\SSK2"
        };
        String[] newArray = new UserSort().addUnit(array);
        Arrays.sort(newArray, new DescendingSort());
        assertThat(newArray, is(new String[] {
                                                "K2",
                                                "K2\\SK1",
                                                "K2\\SK1\\SSK2",
                                                "K2\\SK1\\SSK1",
                                                "K1",
                                                "K1\\SK2",
                                                "K1\\SK1",
                                                "K1\\SK1\\SSK2",
                                                "K1\\SK1\\SSK1"
                                                })
        );
    }
}

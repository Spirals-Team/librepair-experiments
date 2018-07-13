package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MergesortTest {
    @Test
    public void whenMergeTwoSortArraysThenResSortArray1() {
        int[] left= {0,5,8,15,35};
        int[] right= {4,8,33,55,65,88,99,100};
        int[] res={0,4,5,8,8,15,33,35,55,65,88,99,100};
        Mergesort ss=new Mergesort();
        assertThat(ss.msort(left,right),is(res));
    }

    @Test
    public void whenMergeTwoSortArraysThenResSortArray2() {
        int[] left= {4,8,33,55,65,88,99,100};
        int[] right= {0,5,8,15,35};
        int[] res={0,4,5,8,8,15,33,35,55,65,88,99,100};
        Mergesort ss=new Mergesort();
        assertThat(ss.msort(left,right),is(res));
    }
}

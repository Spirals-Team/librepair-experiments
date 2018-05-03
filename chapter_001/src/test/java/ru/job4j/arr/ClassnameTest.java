package ru.job4j.arr;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class ClassnameTest {
    @Test
    public void testPeredavaimogoMassiva() {


        double[][] temp = new double[3][4];
        temp[0] = Classname.methodName1();
        temp[1] = Classname.methodName2();
        temp[2] = Classname.methodName3();
        double[][] expected = {{2.0, 4.0, 6.0, 8.0}, {2.0, 4.0, 6.0, 8.0}, {2.0, 4.0, 6.0, 8.0}};
        assertThat(temp, is(expected));
    }

}

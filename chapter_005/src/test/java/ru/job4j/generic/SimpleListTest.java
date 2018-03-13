package ru.job4j.generic;

//import com.sun.org.apache.xpath.internal.operations.String;
//import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//import ru.job4j.generic.SimpleList;
import java.lang.String;
import java.util.*;


//import java.lang.*;

public class SimpleListTest {

    public class A { }
    public class B extends A { }
    public class C extends B { }

    @Test
    public void whenCreateContainerShouldReturnTheSameType() {
//        SimpleList<String> simple = new SimpleList<String>(4);
        SimpleList simple = new Stack(4);

        simple.add("test");
        String result = (String) simple.get(0);

        assertThat(result, is("test"));
    }

    @Test
    public void whenTypeIntShouldReturnInt() {
//        SimpleList<Integer> simple = new SimpleList<Integer>(4);
        SimpleList simple = new Stack(4);
        simple.add(2);
        int result = (int) simple.get(0);

        assertThat(result, is(2));
    }

    public  void  showList() {
        List<String> list = new ArrayList<>(100);
        List<? super Integer> numbers = new LinkedList<>();
        numbers.add(1);
    }

    @Test
    public void showSet() {
//        Set<String> set = new HashSet<>();
//        Set<String> set = new TreeSet<>();
        Set<String> set = new LinkedHashSet<>();
        set.add("first");
        set.add("second");
        set.add("zero");
        set.add("zero");
        set.add("third");
        for (String value : set) {
            System.out.println(value.hashCode());
            System.out.println(value);
        }
//        System.out.println(set);

    }

//    public void wildTest() {
////        SimpleList<B> list = new SimpleList<>(10);
////        SimpleList<? super A> list = new SimpleList<>(10);
//        SimpleList<? extends B> list = new SimpleList<>(10);
//        list.add(new A());
//        list.add(new B());
//        list.add(new C());
//        print(list);
//
//        printUpper(list);
//
//        printLower(list);
//
//    }
//
//    public void print(SimpleList<?> list) {
//        //todo print
//    }
//
//    public void printUpper(SimpleList<? extends B> list) {
//
//    }
//
//    public void printLower(SimpleList<? super B> list) {
//
//    }

}

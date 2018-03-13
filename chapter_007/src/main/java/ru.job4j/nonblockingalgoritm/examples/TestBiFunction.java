package ru.job4j.nonblockingalgoritm.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

//http://www.topjavatutorial.com/java-8/java-8-map-computeifpresent/
public class TestBiFunction {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        System.out.println(map);

        BiFunction<String, Integer, Integer> biFunction = (k, v) -> v + 1;

        // "A" is already present in map, so its value will be incremented
        map.computeIfPresent("A", biFunction);
        System.out.println(map);

        // Since "D" is not present in map, the computation won't occur
        map.computeIfPresent("D", biFunction);
        System.out.println(map);
    }
}

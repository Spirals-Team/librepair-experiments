package ru.job4j.startcollection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionMap {
    public static void main(String[] args) {
        Map<String, Integer> student = new HashMap<>();
        student.put("Vasia", 5);
        student.put("Peter", 3);
        student.put("Alxandr", 3);
        student.put("Miha", 3);
        student.put(null, 11);
        student.put(null, 12);
        Iterator<Map.Entry<String, Integer>> iterator = student.entrySet().iterator();

        for (String name:student.keySet()) {
            System.out.println(String.format("%s:%s", name,  student.get(name)));
        }
        for (Integer val:student.values()) {
            System.out.println(String.format("%s", val));
        }
    }
}

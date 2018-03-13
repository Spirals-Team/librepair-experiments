package ru.job4j.lite;

import java.util.Map;
import java.util.HashMap;

public class MapExample {
    public static void main(String[] args) {
        Map<String, Integer> students = new HashMap<>();
        students.put("sergey afonin", 3);
        students.put("ivan ivanov", 5);
        students.put("sergey afonin", 5);

        System.out.println(students.get("sergey afonin"));

        for (String student : students.keySet()) {
            System.out.println(String.format("%s : %s", student, students.get(student)));
        }

        for (Integer scope : students.values()) {
            System.out.println(String.format("%s ", scope));
        }
    }
}

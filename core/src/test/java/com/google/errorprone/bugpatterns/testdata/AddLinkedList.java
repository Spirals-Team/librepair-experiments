package com.softconex.gep.testdata;

import java.util.LinkedList;
import java.util.List;

public class AddLinkedList {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("abc");
        // BUG: Diagnostic contains: java.util.LinkedList accesses through via index
        list.add(1, "xyz");
    }
}

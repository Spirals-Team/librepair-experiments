package com.softconex.gep.testdata;

import java.util.LinkedList;
import java.util.List;

public class RemoveLinkedList {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("abc");
        list.add("xyz");
        // BUG: Diagnostic contains: java.util.LinkedList accesses through via index
        list.remove(1);
    }
}

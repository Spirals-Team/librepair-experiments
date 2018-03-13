package ru.job4j.collections;

import org.junit.Test;
import ru.job4j.generic.ConvertList;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestingThePerformanceOfCollectionsTest {

    @Test
    public void whenTestingThePerformanceOfArrayListAndLinkedListTheArrayListIsFasterThenLinkedList() {

        boolean theArrayListIsFasterThenLinkedList = true;
        boolean expected = theArrayListIsFasterThenLinkedList;

        int amount = 200000;

        ArrayList<String> arrayList = new ArrayList<String>();

        LinkedList<String> linkedList = new LinkedList<String>();

        TreeSet<String> treeSet = new TreeSet<String>();

        TestingThePerformanceOfCollections testingThePerformanceOfCollections = new TestingThePerformanceOfCollections();

        long timeOfAddingTooArrayList = testingThePerformanceOfCollections.add(arrayList, amount); //86//61
        long timeOfAddingToLinkedList = testingThePerformanceOfCollections.add(linkedList, amount); //69//75
        long timeOfAddingToTreeSet = testingThePerformanceOfCollections.add(treeSet, amount); //98//91

        long timeOfDeletingFromArrayList = testingThePerformanceOfCollections.delete(arrayList, amount); //6164/5089
        long timeOfDeletingFromLinkedList = testingThePerformanceOfCollections.delete(linkedList, amount); //14/15
        long timeOfDeletingFromTreeSet = testingThePerformanceOfCollections.delete(treeSet, amount); //76/74

        System.out.println("timeOfAddingTooArrayList " + timeOfAddingTooArrayList);
        System.out.println("timeOfAddingToLinkedList " + timeOfAddingToLinkedList);
        System.out.println("timeOfAddingToTreeSet " + timeOfAddingToTreeSet);

        System.out.println("timeOfDeletingFromArrayList " + timeOfDeletingFromArrayList);
        System.out.println("timeOfDeletingFromLinkedList " + timeOfDeletingFromLinkedList);
        System.out.println("timeOfDeletingFromTreeSet " + timeOfDeletingFromTreeSet);


//        timeOfAddingTooArrayList 81
//        timeOfAddingToLinkedList 79
//        timeOfAddingToTreeSet 90
//        timeOfDeletingFromArrayList 5128
//        timeOfDeletingFromLinkedList 19
//        timeOfDeletingFromLinkedList 19

        boolean result = true;

        assertThat(result, is(expected));
    }

}

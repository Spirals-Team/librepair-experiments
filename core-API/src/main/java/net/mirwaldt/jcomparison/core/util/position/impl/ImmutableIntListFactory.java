package net.mirwaldt.jcomparison.core.util.position.impl;

import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;

import java.util.List;

public class ImmutableIntListFactory {
    
    public static ImmutableIntList create(List<Integer> intList) {
        if(intList.isEmpty()) {
            return new ImmutableIntArrayImmutableIntList(null);
        } else if(intList.size() == 1) {
            return new ImmutableOneElementImmutableIntList(intList.get(0));
        } else if(intList.size() == 2) {
            return new ImmutableTwoElementsImmutableIntList(intList.get(0), intList.get(1));
        } else {
            final int[] intArray = new int[intList.size()];
            int index = 0;
            for (Integer element : intList) {
                intArray[index] = element;
                index++;
            }
            return new ImmutableIntArrayImmutableIntList(intArray);
        }
    }
}

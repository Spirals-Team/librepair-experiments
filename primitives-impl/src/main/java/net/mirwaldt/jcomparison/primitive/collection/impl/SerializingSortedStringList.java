package net.mirwaldt.jcomparison.primitive.collection.impl;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayIntList;

public class SerializingSortedStringList {
    final ArrayByteList stringBytes = new ArrayByteList();
    final ArrayIntList startOffsets = new ArrayIntList(); 
    final ArrayIntList searchIndex = new ArrayIntList();       
}

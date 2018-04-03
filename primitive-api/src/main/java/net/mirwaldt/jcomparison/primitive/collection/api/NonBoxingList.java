package net.mirwaldt.jcomparison.primitive.collection.api;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableSupplier;

import java.util.Iterator;
import java.util.List;

public interface NonBoxingList {
    boolean contains(Mutable<?, ?> element);
    
    void clear();

    int add(Mutable<?, ?> element);
    Mutable<?, ?> add(int index, Mutable<?, ?> element);
    Mutable<?, ?> add(int index, Mutable<?, ?> element, MutableSupplier returnedSupplier);
    
    Mutable<?, ?> get(int index);
    Mutable<?, ?> get(int index, MutableSupplier returnedSupplier);
    
    Mutable<?, ?> set(int index, Mutable<?, ?> element);
    Mutable<?, ?> set(int index, Mutable<?, ?> element, MutableSupplier returnedSupplier);

    int remove(Mutable<?, ?> element);
    Mutable<?, ?> remove(int index);
    Mutable<?, ?> remove(int index, MutableSupplier returnedSupplier);
    
    int indexOf(Mutable<?, ?> element);
    int lastIndexOf(Mutable<?, ?> element);
    
    Iterator<Mutable<?, ?>> iterator();
    
    void trimToSize();
    
    int size();

    default boolean isEmpty() {
        return size()==0;
    }
}

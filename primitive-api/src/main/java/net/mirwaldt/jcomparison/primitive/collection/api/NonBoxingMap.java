package net.mirwaldt.jcomparison.primitive.collection.api;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;

import java.util.Iterator;
import java.util.Map;

public interface NonBoxingMap { // extends Map<Object, Object> {
    boolean containsKey(Mutable<?, ?> key);
    boolean containsValue(Mutable<?, ?> value);

    Mutable<?, ?> get(Mutable<?, ?> key);

    Mutable<?, ?> put(Mutable<?, ?> key, Mutable<?, ?> value);

    Mutable<?, ?> remove(Mutable<?, ?> key);

    Iterator<Mutable<?, ?>> mutableKeySetIterator();
    Iterator<Mutable<?, ?>> mutableValuesIterator();

    Iterator<Map.Entry<Mutable<?, ?>, Mutable<?, ?>>> mutableEntrySetIterator();
    
    void trimToSize();
}

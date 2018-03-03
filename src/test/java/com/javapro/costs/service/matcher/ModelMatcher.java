package com.javapro.costs.service.matcher;

import org.junit.Assert;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by unlimit1984 on 04.03.2018.
 */
public class ModelMatcher<T> {

    private Comparator<T> comparator;

    public ModelMatcher() {
        this((T expected, T actual) ->
                expected == actual ? 0 : String.valueOf(expected).compareTo(String.valueOf(actual)));
    }

    public ModelMatcher(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private class Wrapper {
        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
//            return entity != null ? comparator.compare(entity, that.entity) : that.entity == null;
            return entity != null ? (comparator.compare(entity, that.entity) == 0) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    public List<Wrapper> wrap(Collection<T> collection) {
        return collection.stream().map(this::wrap).collect(Collectors.toList());
    }
}

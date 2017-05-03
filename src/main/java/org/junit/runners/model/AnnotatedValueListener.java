package org.junit.runners.model;

public interface AnnotatedValueListener {
    ThreadLocal<AnnotatedValueListener> CURRENT = new ThreadLocal<AnnotatedValueListener>();

    AnnotatedValueListener NOOP = new AnnotatedValueListener() {
        public void acceptValue(FrameworkMember member, Object value) {
        }
    };

    void acceptValue(FrameworkMember member, Object value);
}

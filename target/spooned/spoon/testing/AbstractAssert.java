package spoon.testing;


public abstract class AbstractAssert<T extends spoon.testing.AbstractAssert<T, A>, A> {
    protected final java.util.LinkedList<spoon.processing.Processor<?>> processors = new java.util.LinkedList<>();

    protected final A actual;

    protected final T myself;

    protected AbstractAssert(A actual, java.lang.Class<?> selfType) {
        this.myself = ((T) (selfType.cast(this)));
        this.actual = actual;
    }

    public T withProcessor(spoon.processing.Processor<?> processor) {
        processors.add(processor);
        return myself;
    }

    public T withProcessor(java.lang.Class<? extends spoon.processing.Processor<?>> processor) {
        try {
            withProcessor(processor.newInstance());
        } catch (java.lang.InstantiationException | java.lang.IllegalAccessException e) {
            throw new java.lang.RuntimeException("Can't instante class processor.", e);
        }
        return myself;
    }

    public T withProcessor(java.lang.String qualifiedName) {
        try {
            withProcessor(((java.lang.Class<? extends spoon.processing.Processor<?>>) (java.lang.Thread.currentThread().getContextClassLoader().loadClass(qualifiedName))));
        } catch (java.lang.ClassNotFoundException e) {
            throw new spoon.SpoonException((("Unable to load processor \"" + qualifiedName) + "\""), e);
        }
        return myself;
    }

    public int hashCode() {
        return 1;
    }
}


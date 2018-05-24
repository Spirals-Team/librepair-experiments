package spoon.test.loop.testclasses;


public abstract class Join<T> extends spoon.test.loop.testclasses.Condition<T> {
    final java.util.Collection<spoon.test.loop.testclasses.Condition<? super T>> conditions;

    @java.lang.SafeVarargs
    protected Join(spoon.test.loop.testclasses.Condition<? super T>... conditions) {
        if (conditions == null)
            throw spoon.test.loop.testclasses.Join.conditionsIsNull();

        this.conditions = new java.util.ArrayList<>();
        for (spoon.test.loop.testclasses.Condition<? super T> condition : conditions)
            this.conditions.add(spoon.test.loop.testclasses.Join.notNull(condition));

    }

    private static java.lang.NullPointerException conditionsIsNull() {
        return new java.lang.NullPointerException("The given conditions should not be null");
    }

    private static <T> spoon.test.loop.testclasses.Condition<T> notNull(spoon.test.loop.testclasses.Condition<T> condition) {
        return null;
    }

    protected final java.util.Collection<spoon.test.loop.testclasses.Condition<? super T>> conditions() {
        return java.util.Collections.unmodifiableCollection(conditions);
    }
}


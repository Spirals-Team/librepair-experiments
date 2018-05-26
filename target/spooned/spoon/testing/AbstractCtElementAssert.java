package spoon.testing;


public abstract class AbstractCtElementAssert<T extends spoon.testing.AbstractCtElementAssert<T>> extends spoon.testing.AbstractAssert<T, spoon.reflect.declaration.CtElement> {
    protected AbstractCtElementAssert(spoon.reflect.declaration.CtElement actual, java.lang.Class<?> selfType) {
        super(actual, selfType);
    }

    public T isEqualTo(spoon.reflect.declaration.CtElement expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.Check.assertIsSame(actual, expected);
        spoon.testing.utils.ProcessorUtils.process(actual.getFactory(), processors);
        if (!(actual.equals(expected))) {
            throw new java.lang.AssertionError();
        }
        return this.myself;
    }

    public T isEqualTo(java.lang.String expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.ProcessorUtils.process(actual.getFactory(), processors);
        if (!(actual.toString().equals(expected))) {
            throw new java.lang.AssertionError(java.lang.String.format("%1$s and %2$s aren't equals.", actual.getShortRepresentation(), expected));
        }
        return this.myself;
    }
}


package spoon.testing;


public abstract class AbstractFileAssert<T extends spoon.testing.AbstractFileAssert<T>> extends spoon.testing.AbstractAssert<T, java.io.File> {
    public AbstractFileAssert(java.io.File actual, java.lang.Class<?> selfType) {
        super(actual, selfType);
    }

    public T isEqualTo(java.lang.String expected) {
        return isEqualTo(new java.io.File(expected));
    }

    public T isEqualTo(java.io.File expected) {
        spoon.testing.utils.Check.assertNotNull(expected);
        spoon.testing.utils.Check.assertExists(expected);
        final spoon.reflect.factory.Factory actualFactory = spoon.testing.utils.ModelUtils.build(actual);
        final spoon.reflect.factory.Factory expectedFactory = spoon.testing.utils.ModelUtils.build(expected);
        spoon.testing.utils.ProcessorUtils.process(actualFactory, processors);
        final java.util.List<spoon.reflect.declaration.CtType<?>> allActual = actualFactory.Type().getAll();
        final java.util.List<spoon.reflect.declaration.CtType<?>> allExpected = expectedFactory.Type().getAll();
        for (int i = 0; i < (allActual.size()); i++) {
            final spoon.reflect.declaration.CtType<?> currentActual = allActual.get(i);
            final spoon.reflect.declaration.CtType<?> currentExpected = allExpected.get(i);
            if (!(currentActual.equals(currentExpected))) {
                throw new java.lang.AssertionError(java.lang.String.format("%1$s and %2$s aren't equals.", currentActual.getQualifiedName(), currentExpected.getQualifiedName()));
            }
        }
        return this.myself;
    }
}


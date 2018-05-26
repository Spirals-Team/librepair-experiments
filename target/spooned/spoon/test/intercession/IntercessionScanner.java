package spoon.test.intercession;


public abstract class IntercessionScanner extends spoon.reflect.visitor.CtScanner {
    protected final spoon.reflect.factory.Factory factory;

    protected final java.util.List<spoon.reflect.reference.CtTypeReference<?>> COLLECTIONS;

    protected final spoon.reflect.reference.CtTypeReference<spoon.reflect.declaration.CtElement> CTELEMENT_REFERENCE;

    protected final spoon.reflect.reference.CtTypeReference<java.util.Collection> COLLECTION_REFERENCE;

    protected final spoon.reflect.reference.CtTypeReference<java.util.List> LIST_REFERENCE;

    protected final spoon.reflect.reference.CtTypeReference<java.util.Set> SET_REFERENCE;

    public IntercessionScanner(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        COLLECTION_REFERENCE = factory.Type().createReference(java.util.Collection.class);
        LIST_REFERENCE = factory.Type().createReference(java.util.List.class);
        SET_REFERENCE = factory.Type().createReference(java.util.Set.class);
        COLLECTIONS = java.util.Arrays.asList(COLLECTION_REFERENCE, LIST_REFERENCE, SET_REFERENCE);
        CTELEMENT_REFERENCE = factory.Type().createReference(spoon.reflect.declaration.CtElement.class);
    }

    protected abstract boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate);

    protected abstract void process(spoon.reflect.declaration.CtMethod<?> element);

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
        if (isToBeProcessed(m)) {
            process(m);
        }
        super.visitCtMethod(m);
    }

    protected boolean avoidThrowUnsupportedOperationException(spoon.reflect.declaration.CtMethod<?> candidate) {
        if ((candidate.getBody().getStatements().size()) != 1) {
            return true;
        }
        if (!((candidate.getBody().getStatement(0)) instanceof spoon.reflect.code.CtThrow)) {
            return true;
        }
        spoon.reflect.code.CtThrow ctThrow = candidate.getBody().getStatement(0);
        if (!((ctThrow.getThrownExpression()) instanceof spoon.reflect.code.CtConstructorCall)) {
            return true;
        }
        final spoon.reflect.code.CtConstructorCall<? extends java.lang.Throwable> thrownExpression = ((spoon.reflect.code.CtConstructorCall<? extends java.lang.Throwable>) (ctThrow.getThrownExpression()));
        if (!(thrownExpression.getType().equals(factory.Type().createReference(java.lang.UnsupportedOperationException.class)))) {
            return true;
        }
        return false;
    }

    protected boolean takeSetterForCtElement(spoon.reflect.declaration.CtMethod<?> candidate) {
        return candidate.getParameters().get(0).getType().isSubtypeOf(CTELEMENT_REFERENCE);
    }

    protected boolean avoidInterfaces(spoon.reflect.declaration.CtMethod<?> candidate) {
        return (candidate.getBody()) != null;
    }
}


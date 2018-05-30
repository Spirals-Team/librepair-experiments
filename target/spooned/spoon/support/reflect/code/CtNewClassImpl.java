package spoon.support.reflect.code;


public class CtNewClassImpl<T> extends spoon.support.reflect.code.CtConstructorCallImpl<T> implements spoon.reflect.code.CtNewClass<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    spoon.reflect.declaration.CtClass<?> anonymousClass;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtNewClass(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtClass<?> getAnonymousClass() {
        return anonymousClass;
    }

    @java.lang.Override
    public <N extends spoon.reflect.code.CtNewClass> N setAnonymousClass(spoon.reflect.declaration.CtClass<?> anonymousClass) {
        if (anonymousClass != null) {
            anonymousClass.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.NESTED_TYPE, anonymousClass, this.anonymousClass);
        this.anonymousClass = anonymousClass;
        return ((N) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtNewClass<T> clone() {
        return ((spoon.reflect.code.CtNewClass<T>) (super.clone()));
    }
}


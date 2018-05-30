package spoon.support.reflect.reference;


public class CtParameterReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtParameterReference<T> {
    private static final long serialVersionUID = 1L;

    public CtParameterReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtParameterReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<?> getDeclaringExecutable() {
        spoon.reflect.declaration.CtParameter<T> declaration = getDeclaration();
        if (declaration == null) {
            return null;
        }
        return declaration.getParent().getReference();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtParameter<T> getDeclaration() {
        final spoon.reflect.declaration.CtParameter<T> ctParameter = lookupDynamically();
        if (ctParameter != null) {
            return ctParameter;
        }
        return null;
    }

    private spoon.reflect.declaration.CtParameter<T> lookupDynamically() {
        spoon.reflect.declaration.CtElement element = this;
        spoon.reflect.declaration.CtParameter optional = null;
        java.lang.String name = getSimpleName();
        try {
            do {
                spoon.reflect.declaration.CtExecutable executable = element.getParent(spoon.reflect.declaration.CtExecutable.class);
                if (executable == null) {
                    return null;
                }
                for (spoon.reflect.declaration.CtParameter parameter : ((java.util.List<spoon.reflect.declaration.CtParameter>) (executable.getParameters()))) {
                    if (name.equals(parameter.getSimpleName())) {
                        optional = parameter;
                    }
                }
                element = executable;
            } while (optional == null );
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
        return optional;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtParameterReference<T> clone() {
        return ((spoon.reflect.reference.CtParameterReference<T>) (super.clone()));
    }
}


package spoon.support.reflect.reference;


public class CtLocalVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtLocalVariableReference<T> {
    private static final long serialVersionUID = 1L;

    public CtLocalVariableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtLocalVariableReference(this);
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public spoon.reflect.code.CtLocalVariable<T> getDeclaration() {
        final spoon.reflect.factory.Factory factory = getFactory();
        if (factory == null) {
            return null;
        }
        final java.lang.String simpleName = getSimpleName();
        if ((parent) instanceof spoon.reflect.code.CtLocalVariable) {
            spoon.reflect.code.CtLocalVariable<T> var = ((spoon.reflect.code.CtLocalVariable<T>) (parent));
            if (simpleName.equals(var.getSimpleName())) {
                return var;
            }
        }
        try {
            spoon.reflect.declaration.CtVariable<?> var = map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(simpleName)).first();
            if (var instanceof spoon.reflect.code.CtLocalVariable) {
                return ((spoon.reflect.code.CtLocalVariable<T>) (var));
            }
            if (var != null) {
                return null;
            }
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtLocalVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtLocalVariableReference<T>) (super.clone()));
    }
}


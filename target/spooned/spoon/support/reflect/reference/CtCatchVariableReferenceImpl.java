package spoon.support.reflect.reference;


public class CtCatchVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtCatchVariableReference<T> {
    private static final long serialVersionUID = 1L;

    public CtCatchVariableReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtCatchVariableReference(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtCatchVariable<T> getDeclaration() {
        spoon.reflect.declaration.CtElement element = this;
        java.lang.String name = getSimpleName();
        spoon.reflect.code.CtCatchVariable var;
        try {
            do {
                spoon.reflect.code.CtCatch catchBlock = element.getParent(spoon.reflect.code.CtCatch.class);
                if (catchBlock == null) {
                    return null;
                }
                var = catchBlock.getParameter();
                element = catchBlock;
            } while (!(name.equals(var.getSimpleName())) );
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
        return var;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtCatchVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtCatchVariableReference<T>) (super.clone()));
    }
}


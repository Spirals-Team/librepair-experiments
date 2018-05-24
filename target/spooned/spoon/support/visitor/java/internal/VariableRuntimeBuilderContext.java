package spoon.support.visitor.java.internal;


public class VariableRuntimeBuilderContext extends spoon.support.visitor.java.internal.AbstractRuntimeBuilderContext {
    private spoon.reflect.declaration.CtVariable ctVariable;

    public VariableRuntimeBuilderContext(spoon.reflect.declaration.CtField<?> ctField) {
        super(ctField);
        this.ctVariable = ctField;
    }

    public VariableRuntimeBuilderContext(spoon.reflect.declaration.CtParameter<?> ctParameter) {
        super(ctParameter);
        this.ctVariable = ctParameter;
    }

    @java.lang.Override
    public void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation) {
        ctVariable.addAnnotation(ctAnnotation);
    }

    @java.lang.Override
    public void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference) {
        switch (role) {
            case TYPE :
                ctVariable.setType(ctTypeReference);
                return;
        }
        super.addTypeReference(role, ctTypeReference);
    }
}


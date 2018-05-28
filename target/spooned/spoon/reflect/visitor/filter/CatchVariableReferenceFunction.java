package spoon.reflect.visitor.filter;


public class CatchVariableReferenceFunction extends spoon.reflect.visitor.filter.LocalVariableReferenceFunction {
    public CatchVariableReferenceFunction() {
        super(spoon.reflect.code.CtCatchVariable.class, spoon.reflect.reference.CtCatchVariableReference.class);
    }

    public CatchVariableReferenceFunction(spoon.reflect.code.CtCatchVariable<?> catchVariable) {
        super(spoon.reflect.code.CtCatchVariable.class, spoon.reflect.reference.CtCatchVariableReference.class, catchVariable);
    }
}


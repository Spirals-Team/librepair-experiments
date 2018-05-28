package spoon.reflect.visitor.filter;


public class ParameterReferenceFunction extends spoon.reflect.visitor.filter.LocalVariableReferenceFunction {
    public ParameterReferenceFunction() {
        super(spoon.reflect.declaration.CtParameter.class, spoon.reflect.reference.CtParameterReference.class);
    }

    public ParameterReferenceFunction(spoon.reflect.declaration.CtParameter<?> parameter) {
        super(spoon.reflect.declaration.CtParameter.class, spoon.reflect.reference.CtParameterReference.class, parameter);
    }
}


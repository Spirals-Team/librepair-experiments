package spoon.reflect.visitor.filter;


public class VariableAccessFilter<T extends spoon.reflect.code.CtVariableAccess<?>> implements spoon.reflect.visitor.Filter<T> {
    spoon.reflect.reference.CtVariableReference<?> variable;

    public VariableAccessFilter(spoon.reflect.reference.CtVariableReference<?> variable) {
        if (variable == null) {
            throw new java.lang.IllegalArgumentException("The parameter variable cannot be null.");
        }
        this.variable = variable;
    }

    @java.lang.Override
    public boolean matches(T variableAccess) {
        return variable.equals(variableAccess.getVariable());
    }
}


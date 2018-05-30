package spoon.reflect.visitor.filter;


public class FieldAccessFilter extends spoon.reflect.visitor.filter.VariableAccessFilter<spoon.reflect.code.CtFieldAccess<?>> {
    public FieldAccessFilter(spoon.reflect.reference.CtFieldReference<?> field) {
        super(field);
    }
}


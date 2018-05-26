package spoon.test.template.testclasses.bounds;


public class CheckBoundTemplate extends spoon.template.StatementTemplate {
    public spoon.template.TemplateParameter<java.util.Collection<?>> _col_;

    @java.lang.Override
    public void statement() {
        if ((_col_.S().size()) > 10)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void setVariable(spoon.reflect.declaration.CtVariable<?> var) {
        spoon.reflect.code.CtVariableRead<java.util.Collection<?>> va = var.getFactory().Core().createVariableRead();
        va.setVariable(((spoon.reflect.reference.CtVariableReference<java.util.Collection<?>>) (var.getReference())));
        _col_ = va;
    }
}


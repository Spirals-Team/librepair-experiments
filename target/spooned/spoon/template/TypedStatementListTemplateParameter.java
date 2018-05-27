package spoon.template;


public abstract class TypedStatementListTemplateParameter<R> implements spoon.template.TemplateParameter<R> {
    public TypedStatementListTemplateParameter() {
    }

    public spoon.reflect.code.CtStatementList getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c;
        spoon.reflect.code.CtBlock<?> b;
        c = targetType.getFactory().Class().get(this.getClass());
        if (c == null) {
            c = targetType.getFactory().Class().get(this.getClass());
        }
        spoon.reflect.code.CtStatementList l = targetType.getFactory().Core().createStatementList();
        if ((this) instanceof spoon.template.Template) {
            b = spoon.template.Substitution.substitute(targetType, ((spoon.template.Template<?>) (this)), c.getMethod("statements").getBody());
        }else {
            b = c.getMethod("statements").getBody().clone();
        }
        l.setStatements(b.getStatements());
        return l;
    }

    public R S() {
        return null;
    }

    public abstract R statements() throws java.lang.Throwable;
}


package spoon.template;


public abstract class TypedBlockTemplateParameter<R> implements spoon.template.TemplateParameter<R> {
    public TypedBlockTemplateParameter() {
    }

    public abstract R block() throws java.lang.Throwable;

    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtBlock<R> getSubstitution(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c;
        c = targetType.getFactory().Class().get(this.getClass());
        if (c == null) {
            c = targetType.getFactory().Class().get(this.getClass());
        }
        spoon.reflect.declaration.CtMethod m = c.getMethod("block");
        if ((this) instanceof spoon.template.Template) {
            return spoon.template.Substitution.substitute(targetType, ((spoon.template.Template<?>) (this)), m.getBody());
        }
        return m.getBody().clone();
    }

    public R S() {
        return null;
    }
}


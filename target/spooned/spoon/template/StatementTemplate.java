package spoon.template;


public abstract class StatementTemplate extends spoon.template.AbstractTemplate<spoon.reflect.code.CtStatement> {
    public StatementTemplate() {
    }

    public spoon.reflect.code.CtStatement apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        spoon.reflect.code.CtStatement result = c.getMethod("statement").getBody().getStatements().get(0).clone();
        java.util.List<spoon.reflect.code.CtStatement> statements = new spoon.support.template.SubstitutionVisitor(c.getFactory(), targetType, this).substitute(result);
        if ((statements.size()) > 1) {
            throw new spoon.SpoonException("StatementTemplate cannot return more then one statement");
        }
        return statements.isEmpty() ? null : statements.get(0);
    }

    public java.lang.Void S() {
        return null;
    }

    public abstract void statement() throws java.lang.Throwable;
}


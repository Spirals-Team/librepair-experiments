package spoon.template;


public abstract class StatementTemplate extends spoon.template.AbstractTemplate<spoon.reflect.code.CtStatement> {
    public StatementTemplate() {
    }

    public spoon.reflect.code.CtStatement apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<?> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        spoon.reflect.code.CtStatement patternModel = c.getMethod("statement").getBody().getStatements().get(0);
        java.util.List<spoon.reflect.code.CtStatement> statements = spoon.template.TemplateBuilder.createPattern(patternModel, this).setAddGeneratedBy(isAddGeneratedBy()).substituteList(c.getFactory(), targetType, spoon.reflect.code.CtStatement.class);
        if ((statements.size()) != 1) {
            throw new java.lang.IllegalStateException();
        }
        return statements.get(0);
    }

    public java.lang.Void S() {
        return null;
    }

    public abstract void statement() throws java.lang.Throwable;
}


package spoon.template;


public abstract class ExpressionTemplate<T> extends spoon.template.AbstractTemplate<spoon.reflect.code.CtExpression<T>> {
    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.reflect.code.CtExpression<T> getExpression(spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> p) {
        spoon.reflect.code.CtBlock<?> b = spoon.template.ExpressionTemplate.getExpressionBlock(p);
        return ((spoon.reflect.code.CtReturn<T>) (b.getStatements().get(0))).getReturnedExpression();
    }

    private static spoon.reflect.code.CtBlock<?> getExpressionBlock(spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> p) {
        spoon.reflect.code.CtBlock<?> b = p.getMethod("expression").getBody();
        return b;
    }

    public ExpressionTemplate() {
    }

    public abstract T expression() throws java.lang.Throwable;

    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.code.CtExpression<T> apply(spoon.reflect.declaration.CtType<?> targetType) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.ExpressionTemplate<?>> c = spoon.template.Substitution.getTemplateCtClass(targetType, this);
        spoon.reflect.code.CtBlock<?> block = spoon.template.TemplateBuilder.createPattern(spoon.template.ExpressionTemplate.getExpressionBlock(c), this).setAddGeneratedBy(isAddGeneratedBy()).substituteSingle(targetType, spoon.reflect.code.CtBlock.class);
        if ((block == null) || (block.getStatements().isEmpty())) {
            return null;
        }
        return ((spoon.reflect.code.CtReturn<T>) (block.getStatements().get(0))).getReturnedExpression();
    }

    public T S() {
        return null;
    }
}


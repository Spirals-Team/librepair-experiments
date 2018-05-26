package spoon.test.template.testclasses;


public class AnExpressionTemplate extends spoon.template.ExpressionTemplate {
    @java.lang.Override
    public java.lang.String expression() throws java.lang.Throwable {
        return new java.lang.String(exp.S());
    }

    @spoon.template.Parameter
    spoon.template.TemplateParameter<java.lang.String> exp;

    @spoon.template.Local
    public AnExpressionTemplate(spoon.reflect.code.CtExpression<java.lang.String> block) {
        this.exp = block;
    }
}


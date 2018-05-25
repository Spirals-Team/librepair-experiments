package spoon.test.template.testclasses;


public class InvocationSubstitutionByExpressionTemplate extends spoon.template.BlockTemplate {
    @java.lang.Override
    public void block() throws java.lang.Throwable {
        java.lang.System.out.println(_expression_().substring(1));
        java.lang.System.out.println(_expression_.S().substring(1));
    }

    @spoon.template.Parameter
    spoon.reflect.code.CtExpression<java.lang.String> _expression_;

    @spoon.template.Local
    public InvocationSubstitutionByExpressionTemplate(spoon.reflect.code.CtExpression<java.lang.String> expr) {
        this._expression_ = expr;
    }

    @spoon.template.Local
    java.lang.String _expression_() {
        return null;
    }
}


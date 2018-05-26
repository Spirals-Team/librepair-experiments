package spoon.test.template.testclasses;


public class SubstitutionByExpressionTemplate extends spoon.template.BlockTemplate {
    @java.lang.Override
    public void block() throws java.lang.Throwable {
        java.lang.System.out.println(_expression_.S().substring(1));
    }

    spoon.reflect.code.CtExpression<java.lang.String> _expression_;

    @spoon.template.Local
    public SubstitutionByExpressionTemplate(spoon.reflect.code.CtExpression<java.lang.String> expr) {
        this._expression_ = expr;
    }
}


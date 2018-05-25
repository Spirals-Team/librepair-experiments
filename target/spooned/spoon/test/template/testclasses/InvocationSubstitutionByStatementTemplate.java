package spoon.test.template.testclasses;


public class InvocationSubstitutionByStatementTemplate extends spoon.template.StatementTemplate {
    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        _statement_();
    }

    @spoon.template.Parameter
    spoon.reflect.code.CtStatement _statement_;

    @spoon.template.Local
    public InvocationSubstitutionByStatementTemplate(spoon.reflect.code.CtStatement statement) {
        this._statement_ = statement;
    }

    @spoon.template.Local
    void _statement_() {
    }

    @spoon.template.Local
    void sample() {
        throw new java.lang.RuntimeException("Failed");
    }
}


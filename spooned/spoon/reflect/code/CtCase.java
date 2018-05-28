package spoon.reflect.code;


public interface CtCase<S> extends spoon.reflect.code.CtStatement , spoon.reflect.code.CtStatementList {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<S> getCaseExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtCase<S>> T setCaseExpression(spoon.reflect.code.CtExpression<S> caseExpression);

    @java.lang.Override
    spoon.reflect.code.CtCase<S> clone();
}


package spoon.reflect.code;


public interface CtIf extends spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONDITION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getCondition();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ELSE)
    <S extends spoon.reflect.code.CtStatement> S getElseStatement();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THEN)
    <S extends spoon.reflect.code.CtStatement> S getThenStatement();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONDITION)
    <T extends spoon.reflect.code.CtIf> T setCondition(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ELSE)
    <T extends spoon.reflect.code.CtIf> T setElseStatement(spoon.reflect.code.CtStatement elseStatement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THEN)
    <T extends spoon.reflect.code.CtIf> T setThenStatement(spoon.reflect.code.CtStatement thenStatement);

    @java.lang.Override
    spoon.reflect.code.CtIf clone();
}


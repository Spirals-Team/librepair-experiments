package spoon.reflect.code;


public interface CtReturn<R> extends spoon.reflect.code.CtCFlowBreak , spoon.template.TemplateParameter<java.lang.Void> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<R> getReturnedExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtReturn<R>> T setReturnedExpression(spoon.reflect.code.CtExpression<R> returnedExpression);

    @java.lang.Override
    spoon.reflect.code.CtReturn<R> clone();
}


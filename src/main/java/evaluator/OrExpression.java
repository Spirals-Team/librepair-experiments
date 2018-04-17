package evaluator;

import java.util.Arrays;
import java.util.List;

public class OrExpression<T> extends Expression<T> {

    List<Expression> expressionList;

    public OrExpression(Expression<T>... expressions) {
        this.expressionList = Arrays.asList(expressions);

    }

    @Override
    public boolean interpret(T context) {
        boolean toRet = false;
        for (Expression<T> e : expressionList)
            toRet|= e.interpret(context);

        return toRet;
    }
}

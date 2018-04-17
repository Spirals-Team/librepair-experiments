package evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndExpression<T> extends Expression<T> {

    List<Expression<T>> expressionList;

    public AndExpression(Expression<T>... expressions) {
        this.expressionList = Arrays.asList(expressions);
    }
    
    public AndExpression(List<Expression<T>> expressions) {
        this.expressionList = new ArrayList<>(expressions);
    }

    @Override
    public boolean interpret(T context) {
        boolean toRet = true;
        for (Expression<T> e : expressionList)
            toRet &= e.interpret(context);

        return toRet;
    }
    
    public AndExpression<T> addExpresion(Expression<T> newExpression){
    	
    	List<Expression<T>> exList = new ArrayList<>(this.expressionList);
    	exList.add(newExpression);
    	
    	return new AndExpression<T>(exList);
    	
    }
}

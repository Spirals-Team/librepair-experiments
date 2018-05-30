package spoon.reflect.eval;


public interface PartialEvaluator {
    <R extends spoon.reflect.declaration.CtElement> R evaluate(R element);
}


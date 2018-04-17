package evaluator;

public abstract class Expression<T> {
    public abstract boolean interpret(T context);
}

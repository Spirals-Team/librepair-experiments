package evaluator;

public abstract class Evaluator<E> {

    protected Expression<E> expressions;

    public Evaluator(Expression<E> expressions) {

        this.expressions = expressions;

    }

    public abstract boolean evaluate(E e);

}

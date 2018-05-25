package spoon.support.reflect.code;


public abstract class CtCodeElementImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.code.CtCodeElement {
    private static final long serialVersionUID = 1L;

    public CtCodeElementImpl() {
        super();
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R extends spoon.reflect.code.CtCodeElement> R partiallyEvaluate() {
        spoon.support.reflect.eval.VisitorPartialEvaluator eval = new spoon.support.reflect.eval.VisitorPartialEvaluator();
        return eval.evaluate(((R) (this)));
    }

    @java.lang.Override
    public spoon.reflect.code.CtCodeElement clone() {
        return ((spoon.reflect.code.CtCodeElement) (super.clone()));
    }
}


package spoon.support.reflect.eval;


public class InlinePartialEvaluator extends spoon.reflect.visitor.CtScanner {
    private final spoon.reflect.eval.PartialEvaluator eval;

    public InlinePartialEvaluator(spoon.reflect.eval.PartialEvaluator eval) {
        this.eval = eval;
    }

    @java.lang.Override
    protected void exit(spoon.reflect.declaration.CtElement e) {
        spoon.reflect.declaration.CtElement simplified = eval.evaluate(e);
        if (simplified != null) {
            e.replace(simplified);
        }
    }
}


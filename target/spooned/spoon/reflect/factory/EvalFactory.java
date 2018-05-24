package spoon.reflect.factory;


public class EvalFactory extends spoon.reflect.factory.SubFactory {
    public EvalFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public spoon.reflect.eval.PartialEvaluator createPartialEvaluator() {
        return new spoon.support.reflect.eval.VisitorPartialEvaluator();
    }
}


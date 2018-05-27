package spoon.reflect.visitor;


public class CtDequeScanner extends spoon.reflect.visitor.CtScanner {
    public CtDequeScanner() {
    }

    protected java.util.Deque<spoon.reflect.declaration.CtElement> elementsDeque = new java.util.ArrayDeque<spoon.reflect.declaration.CtElement>();

    protected void exit(spoon.reflect.declaration.CtElement e) {
        spoon.reflect.declaration.CtElement ret = elementsDeque.pop();
        if (ret != e) {
            throw new java.lang.RuntimeException("Unconsitant Stack");
        }
        super.exit(e);
    }

    protected void enter(spoon.reflect.declaration.CtElement e) {
        elementsDeque.push(e);
        super.enter(e);
    }
}


package spoon.reflect.visitor;


public class ModelConsistencyChecker extends spoon.reflect.visitor.CtScanner {
    boolean fixInconsistencies = false;

    boolean fixNullParents = false;

    spoon.compiler.Environment environment;

    java.util.Deque<spoon.reflect.declaration.CtElement> stack = new java.util.ArrayDeque<>();

    public ModelConsistencyChecker(spoon.compiler.Environment environment, boolean fixInconsistencies, boolean fixNullParents) {
        this.environment = environment;
        this.fixInconsistencies = fixInconsistencies;
        this.fixNullParents = fixNullParents;
    }

    @java.lang.Override
    public void enter(spoon.reflect.declaration.CtElement element) {
        if (!(stack.isEmpty())) {
            if ((!(element.isParentInitialized())) || ((element.getParent()) != (stack.peek()))) {
                if (((!(element.isParentInitialized())) && (fixNullParents)) || (((element.getParent()) != (stack.peek())) && (fixInconsistencies))) {
                    element.setParent(stack.peek());
                }else {
                    final java.lang.String name = (element instanceof spoon.reflect.declaration.CtNamedElement) ? " - " + (((spoon.reflect.declaration.CtNamedElement) (element)).getSimpleName()) : "";
                    environment.report(null, org.apache.log4j.Level.WARN, ((((((((element.isParentInitialized() ? "inconsistent" : "null") + " parent for ") + (element.getClass())) + name) + " - ") + (element.getPosition())) + " - ") + (stack.peek().getPosition())));
                    dumpStack();
                }
            }
        }
        stack.push(element);
    }

    @java.lang.Override
    protected void exit(spoon.reflect.declaration.CtElement e) {
        stack.pop();
    }

    private void dumpStack() {
        environment.debugMessage("model consistency checker stack:");
        for (spoon.reflect.declaration.CtElement e : stack) {
            environment.debugMessage(((("    " + (e.getClass().getSimpleName())) + " ") + (e.getPosition().isValidPosition() ? java.lang.String.valueOf(e.getPosition()) : "(?)")));
        }
    }
}


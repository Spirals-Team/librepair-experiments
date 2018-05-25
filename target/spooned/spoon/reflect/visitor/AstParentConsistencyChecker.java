package spoon.reflect.visitor;


public class AstParentConsistencyChecker extends spoon.reflect.visitor.CtScanner {
    private spoon.reflect.declaration.CtElement parent;

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element == null) {
            return;
        }
        if ((((parent) != null) && (element.isParentInitialized())) && ((element.getParent()) != (parent))) {
            throw new java.lang.IllegalStateException((((((spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(element)) + " is set as child of\n") + (spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(element.getParent()))) + "however it is visited as a child of\n") + (spoon.reflect.visitor.AstParentConsistencyChecker.toDebugString(parent))));
        }
        spoon.reflect.declaration.CtElement parent = this.parent;
        this.parent = element;
        super.scan(element);
        this.parent = parent;
    }

    private static java.lang.String toDebugString(spoon.reflect.declaration.CtElement e) {
        return ((((((("Element: " + e) + "\nSignature: ") + (e.getShortRepresentation())) + "\nClass: ") + (e.getClass())) + "\nposition: ") + (e.getPosition())) + "\n";
    }
}


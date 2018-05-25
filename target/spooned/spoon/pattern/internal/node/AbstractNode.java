package spoon.pattern.internal.node;


public abstract class AbstractNode implements spoon.pattern.internal.node.RootNode {
    private boolean simplifyGenerated = false;

    @java.lang.Override
    public java.lang.String toString() {
        return new spoon.pattern.internal.PatternPrinter().printNode(this);
    }

    @java.lang.Override
    public boolean isSimplifyGenerated() {
        return simplifyGenerated;
    }

    @java.lang.Override
    public void setSimplifyGenerated(boolean simplifyGenerated) {
        this.simplifyGenerated = simplifyGenerated;
    }
}


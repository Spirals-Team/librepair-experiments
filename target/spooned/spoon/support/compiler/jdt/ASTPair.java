package spoon.support.compiler.jdt;


public class ASTPair {
    public spoon.reflect.declaration.CtElement element;

    public org.eclipse.jdt.internal.compiler.ast.ASTNode node;

    public ASTPair(spoon.reflect.declaration.CtElement element, org.eclipse.jdt.internal.compiler.ast.ASTNode node) {
        super();
        this.element = element;
        this.node = node;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((element.getClass().getSimpleName()) + "-") + (node.getClass().getSimpleName());
    }
}


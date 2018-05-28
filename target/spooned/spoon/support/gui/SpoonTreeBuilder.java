package spoon.support.gui;


public class SpoonTreeBuilder extends spoon.reflect.visitor.CtScanner {
    java.util.Deque<javax.swing.tree.DefaultMutableTreeNode> nodes;

    javax.swing.tree.DefaultMutableTreeNode root;

    public SpoonTreeBuilder() {
        super();
        root = new javax.swing.tree.DefaultMutableTreeNode("Spoon Tree Root");
        nodes = new java.util.ArrayDeque<>();
        nodes.push(root);
    }

    private void createNode(java.lang.Object o, spoon.reflect.path.CtRole roleInParent) {
        java.lang.String prefix = (roleInParent == null) ? "" : (roleInParent.getCamelCaseName()) + ": ";
        javax.swing.tree.DefaultMutableTreeNode node = new javax.swing.tree.DefaultMutableTreeNode(o) {
            private static final long serialVersionUID = 1L;

            private java.lang.String getASTNodeName() {
                return getUserObject().getClass().getSimpleName().replaceAll("Impl$", "");
            }

            @java.lang.Override
            public java.lang.String toString() {
                java.lang.String nodeName;
                if ((getUserObject()) instanceof spoon.reflect.declaration.CtNamedElement) {
                    nodeName = ((getASTNodeName()) + " - ") + (((spoon.reflect.declaration.CtNamedElement) (getUserObject())).getSimpleName());
                }else {
                    java.lang.String objectRepresentation;
                    try {
                        objectRepresentation = getUserObject().toString();
                    } catch (java.lang.Exception e) {
                        objectRepresentation = "Failed:" + (e.getMessage());
                    }
                    nodeName = ((getASTNodeName()) + " - ") + objectRepresentation;
                }
                return prefix + nodeName;
            }
        };
        nodes.peek().add(node);
        nodes.push(node);
    }

    private spoon.reflect.path.CtRole roleInParent;

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
        roleInParent = role;
        super.scan(role, element);
    }

    @java.lang.Override
    public void enter(spoon.reflect.declaration.CtElement element) {
        createNode(element, roleInParent);
        super.enter(element);
    }

    @java.lang.Override
    public void exit(spoon.reflect.declaration.CtElement element) {
        nodes.pop();
        super.exit(element);
    }

    public javax.swing.tree.DefaultMutableTreeNode getRoot() {
        return root;
    }
}


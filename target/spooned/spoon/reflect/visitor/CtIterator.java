package spoon.reflect.visitor;


public class CtIterator extends spoon.reflect.visitor.CtScanner implements java.util.Iterator<spoon.reflect.declaration.CtElement> {
    private java.util.ArrayDeque<spoon.reflect.declaration.CtElement> deque = new java.util.ArrayDeque<spoon.reflect.declaration.CtElement>() {
        @java.lang.Override
        public boolean addAll(java.util.Collection c) {
            for (java.lang.Object aC : c) {
                this.addFirst(((spoon.reflect.declaration.CtElement) (aC)));
            }
            return (c.size()) > 0;
        }
    };

    private java.util.ArrayDeque<spoon.reflect.declaration.CtElement> current_children = new java.util.ArrayDeque<>();

    public CtIterator(spoon.reflect.declaration.CtElement root) {
        if (root != null) {
            deque.add(root);
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element != null) {
            current_children.addFirst(element);
        }
    }

    @java.lang.Override
    public boolean hasNext() {
        return (deque.size()) > 0;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtElement next() {
        spoon.reflect.declaration.CtElement next = deque.pollFirst();
        current_children.clear();
        next.accept(this);
        deque.addAll(current_children);
        return next;
    }
}


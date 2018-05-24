package spoon.generating.scanner;


class CtBiScannerTemplate extends spoon.reflect.visitor.CtAbstractBiScanner {
    protected java.util.Deque<spoon.reflect.declaration.CtElement> stack = new java.util.ArrayDeque<>();

    protected void enter(spoon.reflect.declaration.CtElement e) {
    }

    protected void exit(spoon.reflect.declaration.CtElement e) {
    }

    public void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        if (other == null) {
            return;
        }
        stack.push(other);
        try {
            element.accept(this);
        } finally {
            stack.pop();
        }
    }

    public void biScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
        biScan(element, other);
    }

    protected void biScan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements, java.util.Collection<? extends spoon.reflect.declaration.CtElement> others) {
        for (java.util.Iterator<? extends spoon.reflect.declaration.CtElement> firstIt = elements.iterator(), secondIt = others.iterator(); (firstIt.hasNext()) && (secondIt.hasNext());) {
            biScan(role, firstIt.next(), secondIt.next());
        }
    }
}


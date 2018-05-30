package spoon.reflect.visitor;


public abstract class CtAbstractBiScanner extends spoon.reflect.visitor.CtAbstractVisitor {
    public abstract void biScan(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other);

    public abstract void biScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other);
}


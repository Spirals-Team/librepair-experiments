package spoon.support.visitor;


public class HashcodeVisitor extends spoon.reflect.visitor.CtInheritanceScanner {
    private int hashCode = 0;

    @java.lang.Override
    public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement e) {
        if ((e.getSimpleName()) != null) {
            hashCode += e.getSimpleName().hashCode();
        }
    }

    @java.lang.Override
    public void scanCtReference(spoon.reflect.reference.CtReference e) {
        hashCode += e.getSimpleName().hashCode();
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport e) {
        if ((e.getReference()) != null) {
            scanCtReference(e.getReference());
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        hashCode += 1;
        super.scan(element);
    }

    public int getHasCode() {
        return hashCode;
    }
}


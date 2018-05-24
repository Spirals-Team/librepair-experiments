package spoon.reflect.visitor;


public interface ImportScanner {
    void computeImports(spoon.reflect.declaration.CtElement element);

    java.util.Collection<spoon.reflect.declaration.CtImport> getAllImports();

    boolean isImported(spoon.reflect.reference.CtReference ref);
}


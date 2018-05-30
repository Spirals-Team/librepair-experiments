package spoon.reflect.cu;


public interface CompilationUnit extends java.io.Serializable , spoon.processing.FactoryAccessor {
    enum UNIT_TYPE {
        TYPE_DECLARATION, PACKAGE_DECLARATION, MODULE_DECLARATION, UNKNOWN;}

    spoon.reflect.cu.CompilationUnit.UNIT_TYPE getUnitType();

    java.io.File getFile();

    void setFile(java.io.File file);

    int[] getLineSeparatorPositions();

    void setLineSeparatorPositions(int[] lineSeparatorPositions);

    java.util.List<java.io.File> getBinaryFiles();

    java.util.List<spoon.reflect.declaration.CtType<?>> getDeclaredTypes();

    void setDeclaredTypes(java.util.List<spoon.reflect.declaration.CtType<?>> types);

    void addDeclaredType(spoon.reflect.declaration.CtType type);

    spoon.reflect.declaration.CtModule getDeclaredModule();

    void setDeclaredModule(spoon.reflect.declaration.CtModule module);

    spoon.reflect.declaration.CtPackage getDeclaredPackage();

    void setDeclaredPackage(spoon.reflect.declaration.CtPackage ctPackage);

    spoon.reflect.declaration.CtType<?> getMainType();

    java.lang.String getOriginalSourceCode();

    int beginOfLineIndex(int index);

    int nextLineIndex(int index);

    int getTabCount(int index);

    java.util.Collection<spoon.reflect.declaration.CtImport> getImports();

    void setImports(java.util.Collection<spoon.reflect.declaration.CtImport> imports);

    spoon.reflect.visitor.printer.change.SourceFragment getRootSourceFragment();

    spoon.reflect.visitor.printer.change.SourceFragment getSourceFragment(spoon.reflect.declaration.CtElement element);
}


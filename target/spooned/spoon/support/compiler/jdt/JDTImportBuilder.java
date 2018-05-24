package spoon.support.compiler.jdt;


class JDTImportBuilder {
    private final org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration declarationUnit;

    private java.lang.String filePath;

    private spoon.reflect.cu.CompilationUnit spoonUnit;

    private org.eclipse.jdt.internal.compiler.env.ICompilationUnit sourceUnit;

    private spoon.reflect.factory.Factory factory;

    private java.util.Set<spoon.reflect.declaration.CtImport> imports;

    JDTImportBuilder(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration declarationUnit, spoon.reflect.factory.Factory factory) {
        this.declarationUnit = declarationUnit;
        this.factory = factory;
        this.sourceUnit = declarationUnit.compilationResult.compilationUnit;
        this.filePath = org.eclipse.jdt.core.compiler.CharOperation.charToString(sourceUnit.getFileName());
        this.spoonUnit = factory.CompilationUnit().getOrCreate(filePath);
        this.imports = new java.util.HashSet<>();
    }

    void build() {
        if (((declarationUnit.imports) == null) || ((declarationUnit.imports.length) == 0)) {
            return;
        }
        for (org.eclipse.jdt.internal.compiler.ast.ImportReference importRef : declarationUnit.imports) {
            java.lang.String importName = importRef.toString();
            if (!(importRef.isStatic())) {
                if (importName.endsWith("*")) {
                    int lastDot = importName.lastIndexOf(".");
                    java.lang.String packageName = importName.substring(0, lastDot);
                    spoon.reflect.declaration.CtPackage ctPackage = this.factory.Package().get(packageName);
                    if (ctPackage != null) {
                        this.imports.add(factory.Type().createImport(ctPackage.getReference()));
                    }
                }else {
                    spoon.reflect.declaration.CtType klass = this.getOrLoadClass(importName);
                    if (klass != null) {
                        this.imports.add(factory.Type().createImport(klass.getReference()));
                    }
                }
            }else {
                int lastDot = importName.lastIndexOf(".");
                java.lang.String className = importName.substring(0, lastDot);
                java.lang.String methodOrFieldName = importName.substring((lastDot + 1));
                spoon.reflect.declaration.CtType klass = this.getOrLoadClass(className);
                if (klass != null) {
                    if (methodOrFieldName.equals("*")) {
                        this.imports.add(factory.Type().createImport(factory.Type().createWildcardStaticTypeMemberReference(klass.getReference())));
                    }else {
                        java.util.List<spoon.reflect.declaration.CtNamedElement> methodOrFields = klass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, methodOrFieldName));
                        if ((methodOrFields.size()) > 0) {
                            spoon.reflect.declaration.CtNamedElement methodOrField = methodOrFields.get(0);
                            this.imports.add(factory.Type().createImport(methodOrField.getReference()));
                        }
                    }
                }
            }
        }
        spoonUnit.setImports(this.imports);
    }

    private spoon.reflect.declaration.CtType getOrLoadClass(java.lang.String className) {
        spoon.reflect.declaration.CtType klass = this.factory.Class().get(className);
        if (klass == null) {
            klass = this.factory.Interface().get(className);
            if (klass == null) {
                try {
                    java.lang.Class zeClass = this.getClass().getClassLoader().loadClass(className);
                    klass = this.factory.Type().get(zeClass);
                    return klass;
                } catch (java.lang.NoClassDefFoundError | java.lang.ClassNotFoundException e) {
                    return null;
                }
            }
        }
        return klass;
    }
}


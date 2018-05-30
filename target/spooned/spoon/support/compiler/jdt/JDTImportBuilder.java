/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.compiler.jdt;


/**
 * Created by urli on 08/08/2017.
 */
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
        // get the CU: it has already been built during model building in JDTBasedSpoonCompiler
        this.spoonUnit = factory.CompilationUnit().getOrCreate(filePath);
        this.imports = new java.util.HashSet<>();
    }

    // package visible method in a package visible class, not in the public API
    void build() {
        // sets the imports of the Spoon compilation unit corresponding to `declarationUnit`
        if (((declarationUnit.imports) == null) || ((declarationUnit.imports.length) == 0)) {
            return;
        }
        for (org.eclipse.jdt.internal.compiler.ast.ImportReference importRef : declarationUnit.imports) {
            java.lang.String importName = importRef.toString();
            if (!(importRef.isStatic())) {
                if (importName.endsWith("*")) {
                    int lastDot = importName.lastIndexOf(".");
                    java.lang.String packageName = importName.substring(0, lastDot);
                    // only get package from the model by traversing from rootPackage the model
                    // it does not use reflection to achieve that
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


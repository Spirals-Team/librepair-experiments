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
package spoon.support.reflect.cu;


/**
 * Implements a compilation unit. In Java, a compilation unit can contain only one
 * public type declaration and other secondary types declarations (not public).
 */
public class CompilationUnitImpl implements spoon.processing.FactoryAccessor , spoon.reflect.cu.CompilationUnit {
    private static final long serialVersionUID = 1L;

    spoon.reflect.factory.Factory factory;

    java.util.List<spoon.reflect.declaration.CtType<?>> declaredTypes = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.COMPILATION_UNIT_DECLARED_TYPES_CONTAINER_DEFAULT_CAPACITY);

    spoon.reflect.declaration.CtPackage ctPackage;

    java.util.Collection<spoon.reflect.declaration.CtImport> imports = new java.util.HashSet<>();

    spoon.reflect.declaration.CtModule ctModule;

    java.io.File file;

    private int[] lineSeparatorPositions;

    private spoon.reflect.visitor.printer.change.SourceFragment rootFragment;

    @java.lang.Override
    public spoon.reflect.cu.CompilationUnit.UNIT_TYPE getUnitType() {
        // we try to guess based on the file name
        if ((file) != null) {
            if (file.getName().equals(spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_MODULE_DECLARATION)) {
                return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.MODULE_DECLARATION;
            }else
                if (file.getName().equals(spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_PACKAGE_DECLARATION)) {
                    return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.PACKAGE_DECLARATION;
                }else {
                    return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.TYPE_DECLARATION;
                }

            // else we just check if there is a declared type
        }else {
            if (getDeclaredTypes().isEmpty()) {
                if ((getDeclaredModule()) != null) {
                    return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.MODULE_DECLARATION;
                }else
                    if ((getDeclaredPackage()) != null) {
                        return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.PACKAGE_DECLARATION;
                    }else {
                        return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.UNKNOWN;
                    }

            }else {
                return spoon.reflect.cu.CompilationUnit.UNIT_TYPE.TYPE_DECLARATION;
            }
        }
    }

    @java.lang.Override
    public java.io.File getFile() {
        return file;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<?> getMainType() {
        if ((getFile()) == null) {
            return getDeclaredTypes().get(0);
        }
        for (spoon.reflect.declaration.CtType<?> t : getDeclaredTypes()) {
            java.lang.String name = getFile().getName();
            name = name.substring(0, name.lastIndexOf("."));
            if (t.getSimpleName().equals(name)) {
                return t;
            }
        }
        throw new java.lang.RuntimeException(((("inconsistent compilation unit: '" + (file)) + "': declared types are ") + (getDeclaredTypes())));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtType<?>> getDeclaredTypes() {
        return java.util.Collections.unmodifiableList(declaredTypes);
    }

    @java.lang.Override
    public void setDeclaredTypes(java.util.List<spoon.reflect.declaration.CtType<?>> types) {
        this.declaredTypes.clear();
        this.declaredTypes.addAll(types);
    }

    @java.lang.Override
    public void addDeclaredType(spoon.reflect.declaration.CtType type) {
        this.declaredTypes.add(type);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModule getDeclaredModule() {
        return this.ctModule;
    }

    @java.lang.Override
    public void setDeclaredModule(spoon.reflect.declaration.CtModule module) {
        this.ctModule = module;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getDeclaredPackage() {
        return ctPackage;
    }

    @java.lang.Override
    public void setDeclaredPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        this.ctPackage = ctPackage;
    }

    @java.lang.Override
    public void setFile(java.io.File file) {
        this.file = file;
    }

    @java.lang.Override
    public java.util.List<java.io.File> getBinaryFiles() {
        final java.util.List<java.io.File> binaries = new java.util.ArrayList<>();
        final java.lang.String output = getFactory().getEnvironment().getBinaryOutputDirectory();
        if (output != null) {
            final java.io.File base = java.nio.file.Paths.get(output, getDeclaredPackage().getQualifiedName().replace(".", java.io.File.separator)).toFile();
            if (base.isDirectory()) {
                for (final spoon.reflect.declaration.CtType type : getDeclaredTypes()) {
                    // Add main type, for instance, 'Foo.class'.
                    final java.lang.String nameOfType = type.getSimpleName();
                    final java.io.File fileOfType = new java.io.File(base, (nameOfType + ".class"));
                    if (fileOfType.isFile()) {
                        binaries.add(fileOfType);
                    }
                    // Add inner/anonymous types, for instance,
                    // 'Foo$Bar.class'. Use 'getElements()' rather than
                    // 'getNestedTypes()' to also fetch inner types of inner
                    // types of inner types ... and so on.
                    for (final spoon.reflect.declaration.CtType inner : type.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtType.class))) {
                        // 'getElements' does not only return inner types but
                        // also returns 'type' itself. Thus, we need to ensure
                        // to not add 'type' twice.
                        if (!(inner.equals(type))) {
                            final java.lang.String nameOfInner = (nameOfType + "$") + (inner.getSimpleName());
                            final java.io.File fileOfInnerType = new java.io.File(base, (nameOfInner + ".class"));
                            if (fileOfInnerType.isFile()) {
                                binaries.add(fileOfInnerType);
                            }
                        }
                    }
                }
            }
        }
        return binaries;
    }

    java.lang.String originalSourceCode;

    @java.lang.Override
    public java.lang.String getOriginalSourceCode() {
        try {
            if ((originalSourceCode) == null) {
                java.io.FileInputStream s = new java.io.FileInputStream(getFile());
                byte[] elementBytes = new byte[s.available()];
                s.read(elementBytes);
                s.close();
                originalSourceCode = new java.lang.String(elementBytes, this.getFactory().getEnvironment().getEncoding());
            }
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
        return originalSourceCode;
    }

    @java.lang.Override
    public int beginOfLineIndex(int index) {
        int cur = index;
        while ((cur >= 0) && ((getOriginalSourceCode().charAt(cur)) != '\n')) {
            cur--;
        } 
        return cur + 1;
    }

    @java.lang.Override
    public int nextLineIndex(int index) {
        int cur = index;
        while ((cur < (getOriginalSourceCode().length())) && ((getOriginalSourceCode().charAt(cur)) != '\n')) {
            cur++;
        } 
        return cur + 1;
    }

    @java.lang.Override
    public int getTabCount(int index) {
        int cur = index;
        int tabCount = 0;
        int whiteSpaceCount = 0;
        while ((cur < (getOriginalSourceCode().length())) && (((getOriginalSourceCode().charAt(cur)) == ' ') || ((getOriginalSourceCode().charAt(cur)) == '\t'))) {
            if ((getOriginalSourceCode().charAt(cur)) == '\t') {
                tabCount++;
            }
            if ((getOriginalSourceCode().charAt(cur)) == ' ') {
                whiteSpaceCount++;
            }
            cur++;
        } 
        tabCount += whiteSpaceCount / (getFactory().getEnvironment().getTabulationSize());
        return tabCount;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtImport> getImports() {
        return this.imports;
    }

    @java.lang.Override
    public void setImports(java.util.Collection<spoon.reflect.declaration.CtImport> imports) {
        this.imports = imports;
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    @java.lang.Override
    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
    }

    boolean autoImport = true;

    public boolean isAutoImport() {
        return autoImport;
    }

    public void setAutoImport(boolean autoImport) {
        this.autoImport = autoImport;
    }

    private spoon.support.reflect.cu.position.PartialSourcePositionImpl myPartialSourcePosition;

    /**
     *
     *
     * @return a {@link SourcePosition} which points to this {@link CompilationUnit}. It always returns same value to safe memory.
     */
    public spoon.reflect.cu.SourcePosition getOrCreatePartialSourcePosition() {
        if ((myPartialSourcePosition) == null) {
            myPartialSourcePosition = new spoon.support.reflect.cu.position.PartialSourcePositionImpl(this);
        }
        return myPartialSourcePosition;
    }

    @java.lang.Override
    public spoon.reflect.visitor.printer.change.SourceFragment getRootSourceFragment() {
        if ((rootFragment) == null) {
            java.lang.String originSourceCode = getOriginalSourceCode();
            rootFragment = new spoon.reflect.visitor.printer.change.SourceFragment(factory.createSourcePosition(this, 0, ((originSourceCode.length()) - 1), getLineSeparatorPositions()));
            for (spoon.reflect.declaration.CtType<?> ctType : declaredTypes) {
                rootFragment.addTreeOfSourceFragmentsOfElement(ctType);
            }
        }
        return rootFragment;
    }

    @java.lang.Override
    public spoon.reflect.visitor.printer.change.SourceFragment getSourceFragment(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragment = getRootSourceFragment();
        spoon.reflect.cu.SourcePosition sp = element.getPosition();
        if ((sp.getCompilationUnit()) != (this)) {
            throw new spoon.SpoonException("Cannot return SourceFragment of element for different CompilationUnit");
        }
        return rootFragment.getSourceFragmentOf(sp.getSourceStart(), ((sp.getSourceEnd()) + 1));
    }

    @java.lang.Override
    public int[] getLineSeparatorPositions() {
        return lineSeparatorPositions;
    }

    @java.lang.Override
    public void setLineSeparatorPositions(int[] lineSeparatorPositions) {
        this.lineSeparatorPositions = lineSeparatorPositions;
    }
}


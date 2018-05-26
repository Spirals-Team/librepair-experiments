package spoon.reflect.factory;


public class CompilationUnitFactory extends spoon.reflect.factory.SubFactory {
    public CompilationUnitFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    private transient java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> cachedCompilationUnits = new java.util.TreeMap<>();

    public java.util.Map<java.lang.String, spoon.reflect.cu.CompilationUnit> getMap() {
        return cachedCompilationUnits;
    }

    public spoon.reflect.cu.CompilationUnit create() {
        spoon.reflect.cu.CompilationUnit cu = factory.Core().createCompilationUnit();
        return cu;
    }

    public spoon.reflect.cu.CompilationUnit getOrCreate(spoon.reflect.declaration.CtPackage ctPackage) {
        if ((ctPackage.getPosition().getCompilationUnit()) != null) {
            return ctPackage.getPosition().getCompilationUnit();
        }else {
            spoon.reflect.declaration.CtModule module;
            if ((factory.getEnvironment().getComplianceLevel()) > 8) {
                module = ctPackage.getParent(spoon.reflect.declaration.CtModule.class);
            }else {
                module = null;
            }
            java.io.File file = this.factory.getEnvironment().getOutputDestinationHandler().getOutputPath(module, ctPackage, null).toFile();
            try {
                java.lang.String path = file.getCanonicalPath();
                spoon.reflect.cu.CompilationUnit result = this.getOrCreate(path);
                result.setDeclaredPackage(ctPackage);
                ctPackage.setPosition(this.factory.createPartialSourcePosition(result));
                return result;
            } catch (java.io.IOException e) {
                throw new spoon.SpoonException(("Cannot get path for file: " + (file.getAbsolutePath())), e);
            }
        }
    }

    public spoon.reflect.cu.CompilationUnit getOrCreate(spoon.reflect.declaration.CtType type) {
        if (type == null) {
            return null;
        }
        if ((type.getPosition().getCompilationUnit()) != null) {
            return type.getPosition().getCompilationUnit();
        }
        if (type.isTopLevel()) {
            spoon.reflect.declaration.CtModule module;
            if (((type.getPackage()) != null) && ((factory.getEnvironment().getComplianceLevel()) > 8)) {
                module = type.getPackage().getParent(spoon.reflect.declaration.CtModule.class);
            }else {
                module = null;
            }
            java.io.File file = this.factory.getEnvironment().getOutputDestinationHandler().getOutputPath(module, type.getPackage(), type).toFile();
            try {
                java.lang.String path = file.getCanonicalPath();
                spoon.reflect.cu.CompilationUnit result = this.getOrCreate(path);
                result.setDeclaredPackage(type.getPackage());
                result.addDeclaredType(type);
                type.setPosition(this.factory.createPartialSourcePosition(result));
                return result;
            } catch (java.io.IOException e) {
                throw new spoon.SpoonException(("Cannot get path for file: " + (file.getAbsolutePath())), e);
            }
        }else {
            return getOrCreate(type.getTopLevelType());
        }
    }

    public spoon.reflect.cu.CompilationUnit getOrCreate(spoon.reflect.declaration.CtModule module) {
        if ((module.getPosition().getCompilationUnit()) != null) {
            return module.getPosition().getCompilationUnit();
        }else {
            java.io.File file = this.factory.getEnvironment().getOutputDestinationHandler().getOutputPath(module, null, null).toFile();
            try {
                java.lang.String path = file.getCanonicalPath();
                spoon.reflect.cu.CompilationUnit result = this.getOrCreate(path);
                result.setDeclaredModule(module);
                module.setPosition(this.factory.createPartialSourcePosition(result));
                return result;
            } catch (java.io.IOException e) {
                throw new spoon.SpoonException(("Cannot get path for file: " + (file.getAbsolutePath())), e);
            }
        }
    }

    public spoon.reflect.cu.CompilationUnit getOrCreate(java.lang.String filePath) {
        spoon.reflect.cu.CompilationUnit cu = cachedCompilationUnits.get(filePath);
        if (cu == null) {
            if (filePath.startsWith(spoon.support.compiler.jdt.JDTSnippetCompiler.SNIPPET_FILENAME_PREFIX)) {
                cu = factory.Core().createCompilationUnit();
                cachedCompilationUnits.put(filePath, cu);
                return cu;
            }
            cu = factory.Core().createCompilationUnit();
            cu.setFile(new java.io.File(filePath));
            cachedCompilationUnits.put(filePath, cu);
        }
        return cu;
    }

    public spoon.reflect.cu.CompilationUnit removeFromCache(java.lang.String filePath) {
        return cachedCompilationUnits.remove(filePath);
    }
}


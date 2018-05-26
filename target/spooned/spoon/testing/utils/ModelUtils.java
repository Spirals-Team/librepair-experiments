package spoon.testing.utils;


public final class ModelUtils {
    private ModelUtils() {
        throw new java.lang.AssertionError();
    }

    public static spoon.reflect.factory.Factory createFactory() {
        return new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
    }

    public static <T extends spoon.reflect.declaration.CtType<?>> T build(java.lang.String packageName, java.lang.String className) throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((((("./src/test/java/" + (packageName.replace('.', '/'))) + "/") + className) + ".java")));
        comp.build();
        return comp.getFactory().Package().get(packageName).getType(className);
    }

    public static <T extends spoon.reflect.declaration.CtType<?>> T build(java.lang.String packageName, java.lang.String className, final spoon.reflect.factory.Factory f) throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher() {
            @java.lang.Override
            public spoon.reflect.factory.Factory createFactory() {
                return f;
            }
        };
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((((("./src/test/java/" + (packageName.replace('.', '/'))) + "/") + className) + ".java")));
        comp.build();
        return comp.getFactory().Package().get(packageName).getType(className);
    }

    public static spoon.reflect.factory.Factory build(java.lang.Class<?>... classesToBuild) throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        for (java.lang.Class<?> classToBuild : classesToBuild) {
            comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((("./src/test/java/" + (classToBuild.getName().replace('.', '/'))) + ".java")));
        }
        comp.build();
        return comp.getFactory();
    }

    public static spoon.reflect.factory.Factory buildNoClasspath(java.lang.Class<?>... classesToBuild) throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        for (java.lang.Class<?> classToBuild : classesToBuild) {
            comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((("./src/test/java/" + (classToBuild.getName().replace('.', '/'))) + ".java")));
        }
        comp.build();
        return comp.getFactory();
    }

    public static spoon.reflect.factory.Factory build(java.io.File... filesToBuild) {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        for (java.io.File fileToBuild : filesToBuild) {
            try {
                comp.addInputSource(spoon.compiler.SpoonResourceHelper.createResource(fileToBuild));
            } catch (java.io.FileNotFoundException e) {
                throw new java.lang.RuntimeException("File not found", e);
            }
        }
        comp.build();
        return comp.getFactory();
    }

    public static <T> spoon.reflect.declaration.CtType<T> buildClass(java.lang.Class<T> classToBuild) throws java.lang.Exception {
        return spoon.testing.utils.ModelUtils.build(classToBuild).Type().get(classToBuild);
    }

    public static void canBeBuilt(java.io.File outputDirectoryFile, int complianceLevel) {
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDirectoryFile, complianceLevel, false);
    }

    public static void canBeBuilt(java.lang.String outputDirectory, int complianceLevel) {
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDirectory, complianceLevel, false);
    }

    public static void canBeBuilt(java.io.File outputDirectoryFile, int complianceLevel, boolean noClasspath) {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        factory.getEnvironment().setComplianceLevel(complianceLevel);
        factory.getEnvironment().setNoClasspath(noClasspath);
        final spoon.SpoonModelBuilder compiler = launcher.createCompiler(factory);
        compiler.addInputSource(outputDirectoryFile);
        try {
            compiler.build();
        } catch (java.lang.Exception e) {
            final java.lang.AssertionError error = new java.lang.AssertionError(((("Can't compile " + (outputDirectoryFile.getName())) + " because ") + (e.getMessage())));
            error.initCause(e);
            throw error;
        }
    }

    public static void canBeBuilt(java.lang.String outputDirectory, int complianceLevel, boolean noClasspath) {
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File(outputDirectory), complianceLevel, noClasspath);
    }

    public static java.lang.String getOptimizedString(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString().replaceAll("[\\r\\n\\t]+", "").replaceAll("\\s{2,}", " ");
    }
}


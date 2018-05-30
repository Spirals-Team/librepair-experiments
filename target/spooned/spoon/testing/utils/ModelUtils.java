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
package spoon.testing.utils;


public final class ModelUtils {
    private ModelUtils() {
        throw new java.lang.AssertionError();
    }

    public static spoon.reflect.factory.Factory createFactory() {
        return new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
    }

    /**
     * Utility method for testing: creates the model of `packageName` from src/test/java and returns the CtType corresponding to `className`
     */
    public static <T extends spoon.reflect.declaration.CtType<?>> T build(java.lang.String packageName, java.lang.String className) throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((((("./src/test/java/" + (packageName.replace('.', '/'))) + "/") + className) + ".java")));
        comp.build();
        return comp.getFactory().Package().get(packageName).getType(className);
    }

    /**
     * Utility method for testing: creates the model of `packageName` and the factory from src/test/java and returns the CtType corresponding to `className`
     */
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

    /**
     * Utility method for testing: creates the model of the given `classesToBuild` from src/test/java and returns the factory
     */
    public static spoon.reflect.factory.Factory build(java.lang.Class<?>... classesToBuild) throws java.lang.Exception {
        spoon.SpoonModelBuilder comp = new spoon.Launcher().createCompiler();
        for (java.lang.Class<?> classToBuild : classesToBuild) {
            comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((("./src/test/java/" + (classToBuild.getName().replace('.', '/'))) + ".java")));
        }
        comp.build();
        return comp.getFactory();
    }

    /**
     * Utility method for testing: creates the noclasspath model of the given `classesToBuild` from src/test/java and returns the factory
     */
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

    /**
     * Builds the Spoon mode of the `filesToBuild` given as parameter
     */
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

    /**
     * Builds and returns the Spoon model of `` classToBuild
     */
    public static <T> spoon.reflect.declaration.CtType<T> buildClass(java.lang.Class<T> classToBuild) throws java.lang.Exception {
        return spoon.testing.utils.ModelUtils.build(classToBuild).Type().get(classToBuild);
    }

    /**
     * checks that the file `outputDirectoryFile` can be parsed with Spoon , given a compliance level.
     */
    public static void canBeBuilt(java.io.File outputDirectoryFile, int complianceLevel) {
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDirectoryFile, complianceLevel, false);
    }

    /**
     * checks that the file at path `outputDirectory` can be parsed with Spoon , given a compliance level.
     */
    public static void canBeBuilt(java.lang.String outputDirectory, int complianceLevel) {
        spoon.testing.utils.ModelUtils.canBeBuilt(outputDirectory, complianceLevel, false);
    }

    /**
     * checks that the file `outputDirectoryFile` can be parsed with Spoon , given a compliance level and the noclasspath option.
     */
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

    /**
     * checks that the file at path `outputDirectory` can be parsed with Spoon , given a compliance level and noclasspath option.
     */
    public static void canBeBuilt(java.lang.String outputDirectory, int complianceLevel, boolean noClasspath) {
        spoon.testing.utils.ModelUtils.canBeBuilt(new java.io.File(outputDirectory), complianceLevel, noClasspath);
    }

    /**
     * Converts `obj` to String and all EOLs and TABs are removed and sequences of white spaces are replaced by single space
     *
     * @param obj
     * 		to be converted object
     * @return single line string optimized for comparation
     */
    public static java.lang.String getOptimizedString(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString().replaceAll("[\\r\\n\\t]+", "").replaceAll("\\s{2,}", " ");
    }
}


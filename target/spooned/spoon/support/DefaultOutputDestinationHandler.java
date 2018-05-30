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
package spoon.support;


/**
 * Default behavior for the destination of the spoon.
 */
public class DefaultOutputDestinationHandler implements spoon.support.OutputDestinationHandler {
    private java.io.File defaultOutputDirectory;

    private spoon.compiler.Environment environment;

    public DefaultOutputDestinationHandler(java.io.File defaultOutputDirectory, spoon.compiler.Environment environment) {
        this.defaultOutputDirectory = defaultOutputDirectory;
        this.environment = environment;
    }

    @java.lang.Override
    public java.nio.file.Path getOutputPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        java.nio.file.Path directory = getDirectoryPath(module, pack, type);
        java.nio.file.Path moduleDir = getModulePath(module);
        java.nio.file.Path packagePath = getPackagePath(pack);
        java.lang.String fileName = getFileName(pack, type);
        return java.nio.file.Paths.get(directory.toString(), moduleDir.toString(), packagePath.toString(), fileName);
    }

    /**
     *
     *
     * @return return the filename of the current element
     * @param pack
     * 		
     * @param type
     * 		
     */
    protected java.lang.String getFileName(spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        java.lang.String fileName;
        if (type != null) {
            fileName = (type.getSimpleName()) + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION);
        }else
            if (pack != null) {
                fileName = spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_PACKAGE_DECLARATION;
            }else {
                fileName = spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_MODULE_DECLARATION;
            }

        return fileName;
    }

    /**
     *
     *
     * @return the path of the package
     * @param pack
     * 		
     */
    protected java.nio.file.Path getPackagePath(spoon.reflect.declaration.CtPackage pack) {
        java.nio.file.Path packagePath = java.nio.file.Paths.get(".");
        if ((pack != null) && (!(pack.isUnnamedPackage()))) {
            packagePath = java.nio.file.Paths.get(pack.getQualifiedName().replace('.', java.io.File.separatorChar));
        }
        return packagePath;
    }

    /**
     *
     *
     * @return return the path of the module
     * @param module
     * 		
     */
    protected java.nio.file.Path getModulePath(spoon.reflect.declaration.CtModule module) {
        java.nio.file.Path moduleDir = java.nio.file.Paths.get(".");
        if (((module != null) && (!(module.isUnnamedModule()))) && ((environment.getComplianceLevel()) > 8)) {
            moduleDir = java.nio.file.Paths.get(module.getSimpleName());
        }
        return moduleDir;
    }

    /**
     *
     *
     * @return the root path of the destination
     * @param module
     * 		
     * @param pack
     * 		
     * @param type
     * 		
     */
    protected java.nio.file.Path getDirectoryPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        return java.nio.file.Paths.get(getDefaultOutputDirectory().getAbsolutePath());
    }

    @java.lang.Override
    public java.io.File getDefaultOutputDirectory() {
        return defaultOutputDirectory;
    }

    public spoon.compiler.Environment getEnvironment() {
        return environment;
    }
}


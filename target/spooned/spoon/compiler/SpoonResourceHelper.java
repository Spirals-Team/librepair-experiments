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
package spoon.compiler;


/**
 * This class defines a helper for manipulating resources.
 */
public abstract class SpoonResourceHelper {
    private SpoonResourceHelper() {
    }

    /**
     * Tells if the given file is an archive file.
     */
    public static boolean isArchive(java.io.File f) {
        return (f.getName().endsWith(".jar")) || (f.getName().endsWith(".zip"));
    }

    /**
     * Tells if the given file is file (files are not archives).
     */
    public static boolean isFile(java.io.File f) {
        return (f.isFile()) && (!(spoon.compiler.SpoonResourceHelper.isArchive(f)));
    }

    /**
     * Creates the list of {@link SpoonResource} corresponding to the given
     * paths (files, folders, archives).
     */
    public static java.util.List<spoon.compiler.SpoonResource> resources(java.lang.String... paths) throws java.io.FileNotFoundException {
        java.util.List<spoon.compiler.SpoonResource> files = new java.util.ArrayList<>();
        for (java.lang.String path : paths) {
            files.add(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(path)));
        }
        return files;
    }

    /**
     * Creates the {@link SpoonFile} corresponding to the given file.
     */
    public static spoon.compiler.SpoonFile createFile(java.io.File f) throws java.io.FileNotFoundException {
        if (!(f.exists())) {
            throw new java.io.FileNotFoundException(f.toString());
        }
        return new spoon.support.compiler.FileSystemFile(f);
    }

    /**
     * Creates the {@link SpoonResource} corresponding to the given file.
     */
    public static spoon.compiler.SpoonResource createResource(java.io.File f) throws java.io.FileNotFoundException {
        if (spoon.compiler.SpoonResourceHelper.isFile(f)) {
            return spoon.compiler.SpoonResourceHelper.createFile(f);
        }
        return spoon.compiler.SpoonResourceHelper.createFolder(f);
    }

    /**
     * Creates the {@link SpoonFolder} corresponding to the given file.
     */
    public static spoon.compiler.SpoonFolder createFolder(java.io.File f) throws java.io.FileNotFoundException {
        if (!(f.exists())) {
            throw new java.io.FileNotFoundException(((f.toString()) + " does not exist"));
        }
        try {
            if (f.isDirectory()) {
                return new spoon.support.compiler.FileSystemFolder(f);
            }
            if (spoon.compiler.SpoonResourceHelper.isArchive(f)) {
                return new spoon.support.compiler.ZipFolder(f);
            }
        } catch (java.io.IOException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}


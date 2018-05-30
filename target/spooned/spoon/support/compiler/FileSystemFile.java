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
package spoon.support.compiler;


public class FileSystemFile implements spoon.compiler.SpoonFile {
    java.io.File file;

    public FileSystemFile(java.lang.String path) {
        this(new java.io.File(path));
    }

    public FileSystemFile(java.io.File file) {
        super();
        try {
            this.file = file.getCanonicalFile();
        } catch (java.io.IOException e) {
            throw new spoon.SpoonException(e);
        }
    }

    public java.io.InputStream getContent() {
        try {
            if (!(this.file.exists())) {
                throw new java.io.FileNotFoundException(("The following file does not exist: " + (this.file.getCanonicalPath())));
            }
            return new java.io.FileInputStream(file);
        } catch (java.io.IOException e) {
            throw new spoon.SpoonException(e);
        }
    }

    public java.lang.String getName() {
        return file.getName();
    }

    public spoon.compiler.SpoonFolder getParent() {
        try {
            return spoon.compiler.SpoonResourceHelper.createFolder(file.getParentFile());
        } catch (java.io.FileNotFoundException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @java.lang.Override
    public java.io.File getFileSystemParent() {
        return file.getParentFile();
    }

    public boolean isFile() {
        return true;
    }

    public boolean isJava() {
        return getName().endsWith(".java");
    }

    public java.lang.String getPath() {
        try {
            return file.getCanonicalPath();
        } catch (java.lang.Exception e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
            return file.getPath();
        }
    }

    @java.lang.Override
    public java.lang.String toString() {
        return file.getPath();
    }

    @java.lang.Override
    public boolean isArchive() {
        return spoon.compiler.SpoonResourceHelper.isArchive(file);
    }

    @java.lang.Override
    public java.io.File toFile() {
        return file;
    }

    @java.lang.Override
    public boolean isActualFile() {
        return true;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        return toFile().equals(((spoon.compiler.SpoonResource) (obj)).toFile());
    }

    @java.lang.Override
    public int hashCode() {
        return toFile().hashCode();
    }
}


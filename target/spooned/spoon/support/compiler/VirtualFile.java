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


public class VirtualFile implements spoon.compiler.SpoonFile {
    java.lang.String content;

    java.lang.String name = "virtual_file";

    public VirtualFile(java.lang.String content) {
        this.content = content;
    }

    public VirtualFile(java.lang.String contents, java.lang.String name) {
        this(contents);
        this.name = name;
    }

    public java.io.InputStream getContent() {
        return new java.io.ByteArrayInputStream(content.getBytes());
    }

    public boolean isJava() {
        return true;
    }

    public java.lang.String getName() {
        return name;
    }

    public spoon.compiler.SpoonFolder getParent() {
        return new spoon.support.compiler.VirtualFolder();
    }

    @java.lang.Override
    public java.io.File getFileSystemParent() {
        return null;
    }

    public java.lang.String getPath() {
        return name;
    }

    public boolean isFile() {
        return true;
    }

    @java.lang.Override
    public boolean isArchive() {
        return false;
    }

    @java.lang.Override
    public java.io.File toFile() {
        return null;
    }

    @java.lang.Override
    public boolean isActualFile() {
        return false;
    }
}


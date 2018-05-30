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


public class VirtualFolder implements spoon.compiler.SpoonFolder {
    protected final java.util.Set<spoon.compiler.SpoonFile> files = new java.util.HashSet<>();

    @java.lang.Override
    public void addFile(spoon.compiler.SpoonFile o) {
        files.add(o);
    }

    @java.lang.Override
    public void addFolder(spoon.compiler.SpoonFolder o) {
        for (spoon.compiler.SpoonFile f : o.getAllFiles()) {
            if (f.isFile()) {
                files.add(f);
            }
        }
    }

    @java.lang.Override
    public java.util.List<spoon.compiler.SpoonFile> getAllFiles() {
        java.util.List<spoon.compiler.SpoonFile> result = new java.util.ArrayList<>();
        for (spoon.compiler.SpoonFile f : getFiles()) {
            // we take care not to add a file that was already found in a folder
            if (!(result.contains(f))) {
                result.add(f);
            }
        }
        return result;
    }

    @java.lang.Override
    public java.util.List<spoon.compiler.SpoonFile> getAllJavaFiles() {
        java.util.List<spoon.compiler.SpoonFile> result = new java.util.ArrayList<>();
        for (spoon.compiler.SpoonFile f : getAllFiles()) {
            if (f.isJava()) {
                result.add(f);
            }
        }
        return result;
    }

    @java.lang.Override
    public java.util.List<spoon.compiler.SpoonFile> getFiles() {
        return java.util.Collections.unmodifiableList(new java.util.ArrayList<>(files));
    }

    @java.lang.Override
    public java.lang.String getName() {
        return "Virtual directory";
    }

    @java.lang.Override
    public spoon.compiler.SpoonFolder getParent() {
        return null;
    }

    @java.lang.Override
    public java.util.List<spoon.compiler.SpoonFolder> getSubFolders() {
        java.util.List<spoon.compiler.SpoonFolder> result = new java.util.ArrayList<>();
        for (spoon.compiler.SpoonFile f : getAllFiles()) {
            spoon.compiler.SpoonFolder folder = f.getParent();
            if ((folder != null) && (!(result.contains(folder)))) {
                result.add(folder);
            }
        }
        return java.util.Collections.unmodifiableList(result);
    }

    @java.lang.Override
    public boolean isFile() {
        return false;
    }

    @java.lang.Override
    public java.lang.String getPath() {
        // it has to be real path for snippet building
        return ".";
    }

    @java.lang.Override
    public java.io.File getFileSystemParent() {
        return null;
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
    public java.lang.String toString() {
        return "<virtual folder>: " + (super.toString());
    }
}


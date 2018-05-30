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
 * A Spoon resource that represents a folder.
 */
public interface SpoonFolder extends spoon.compiler.SpoonResource {
    /**
     * Gets all the files (excluding folders) in the folder.
     */
    java.util.List<spoon.compiler.SpoonFile> getFiles();

    /**
     * Gets all the files (including folders) in the folder.
     */
    java.util.List<spoon.compiler.SpoonFile> getAllFiles();

    /**
     * Gets all the Java source files in the folder.
     */
    java.util.List<spoon.compiler.SpoonFile> getAllJavaFiles();

    /**
     * Gets the subfolders in this folder.
     */
    java.util.List<spoon.compiler.SpoonFolder> getSubFolders();

    /**
     * Adds a file in this folder
     */
    void addFile(spoon.compiler.SpoonFile source);

    /**
     * Adds a sub folder in this folder
     */
    void addFolder(spoon.compiler.SpoonFolder source);
}


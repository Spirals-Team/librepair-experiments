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


public class ZipFolder implements spoon.compiler.SpoonFolder {
    java.io.File file;

    java.util.List<spoon.compiler.SpoonFile> files;

    public ZipFolder(java.io.File file) throws java.io.IOException {
        super();
        if (!(file.isFile())) {
            throw new java.io.IOException(((file.getName()) + " is not a valid zip file"));
        }
        this.file = file;
    }

    public java.util.List<spoon.compiler.SpoonFile> getAllFiles() {
        return getFiles();
    }

    public java.util.List<spoon.compiler.SpoonFile> getAllJavaFiles() {
        java.util.List<spoon.compiler.SpoonFile> files = new java.util.ArrayList<>();
        for (spoon.compiler.SpoonFile f : getFiles()) {
            if (f.isJava()) {
                files.add(f);
            }
        }
        // no subfolder, skipping
        // for (CtFolder fol : getSubFolder())
        // files.addAll(fol.getAllJavaFile());
        return files;
    }

    public java.util.List<spoon.compiler.SpoonFile> getFiles() {
        // Indexing content
        if ((files) == null) {
            files = new java.util.ArrayList<>();
            java.util.zip.ZipInputStream zipInput = null;
            try {
                zipInput = new java.util.zip.ZipInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
                java.util.zip.ZipEntry entry;
                while ((entry = zipInput.getNextEntry()) != null) {
                    // deflate in buffer
                    final int buffer = 2048;
                    java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream(buffer);
                    int count;
                    byte[] data = new byte[buffer];
                    while ((count = zipInput.read(data, 0, buffer)) != (-1)) {
                        output.write(data, 0, count);
                    } 
                    output.flush();
                    output.close();
                    files.add(new spoon.support.compiler.ZipFile(this, entry.getName(), output.toByteArray()));
                } 
                zipInput.close();
            } catch (java.lang.Exception e) {
                spoon.Launcher.LOGGER.error(e.getMessage(), e);
            }
        }
        return files;
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

    public java.util.List<spoon.compiler.SpoonFolder> getSubFolders() {
        return new java.util.ArrayList<>(0);
    }

    public boolean isFile() {
        return false;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return getPath();
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
    public boolean isArchive() {
        return true;
    }

    @java.lang.Override
    public java.io.File getFileSystemParent() {
        return file.getParentFile();
    }

    @java.lang.Override
    public java.io.File toFile() {
        return file;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        return toString().equals(obj.toString());
    }

    @java.lang.Override
    public int hashCode() {
        return toString().hashCode();
    }

    @java.lang.Override
    public void addFile(spoon.compiler.SpoonFile source) {
        throw new java.lang.UnsupportedOperationException("not possible a real folder");
    }

    @java.lang.Override
    public void addFolder(spoon.compiler.SpoonFolder source) {
        throw new java.lang.UnsupportedOperationException("not possible a real folder");
    }

    /**
     * physically extracts on disk all files of this zip file in the destinationDir `destDir`
     */
    public void extract(java.io.File destDir) {
        java.util.zip.ZipInputStream zipInput = null;
        try {
            zipInput = new java.util.zip.ZipInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
            java.util.zip.ZipEntry entry;
            while ((entry = zipInput.getNextEntry()) != null) {
                java.io.File f = new java.io.File(((destDir + (java.io.File.separator)) + (entry.getName())));
                if (entry.isDirectory()) {
                    // if its a directory, create it
                    f.mkdir();
                    continue;
                }
                // deflate in buffer
                final int buffer = 2048;
                // Force parent directory creation, sometimes directory was not yet handled
                f.getParentFile().mkdirs();
                // in the zip entry iteration
                java.io.OutputStream output = new java.io.BufferedOutputStream(new java.io.FileOutputStream(f));
                int count;
                byte[] data = new byte[buffer];
                while ((count = zipInput.read(data, 0, buffer)) != (-1)) {
                    output.write(data, 0, count);
                } 
                output.flush();
                output.close();
            } 
            zipInput.close();
        } catch (java.lang.Exception e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
    }
}


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
        return files;
    }

    public java.util.List<spoon.compiler.SpoonFile> getFiles() {
        if ((files) == null) {
            files = new java.util.ArrayList<>();
            java.util.zip.ZipInputStream zipInput = null;
            try {
                zipInput = new java.util.zip.ZipInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
                java.util.zip.ZipEntry entry;
                while ((entry = zipInput.getNextEntry()) != null) {
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

    public void extract(java.io.File destDir) {
        java.util.zip.ZipInputStream zipInput = null;
        try {
            zipInput = new java.util.zip.ZipInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
            java.util.zip.ZipEntry entry;
            while ((entry = zipInput.getNextEntry()) != null) {
                java.io.File f = new java.io.File(((destDir + (java.io.File.separator)) + (entry.getName())));
                if (entry.isDirectory()) {
                    f.mkdir();
                    continue;
                }
                final int buffer = 2048;
                f.getParentFile().mkdirs();
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


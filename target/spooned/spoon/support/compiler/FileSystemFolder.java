package spoon.support.compiler;


public class FileSystemFolder implements spoon.compiler.SpoonFolder {
    java.io.File file;

    public FileSystemFolder(java.io.File file) {
        super();
        if (!(file.isDirectory())) {
            throw new spoon.SpoonException(("Not a directory " + file));
        }
        try {
            this.file = file.getCanonicalFile();
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
    }

    public FileSystemFolder(java.lang.String path) {
        this(new java.io.File(path));
    }

    public java.util.List<spoon.compiler.SpoonFile> getAllFiles() {
        java.util.List<spoon.compiler.SpoonFile> all = new java.util.ArrayList<>(getFiles());
        for (spoon.compiler.SpoonFolder f : getSubFolders()) {
            all.addAll(f.getAllFiles());
        }
        return all;
    }

    public java.util.List<spoon.compiler.SpoonFile> getFiles() {
        java.util.List<spoon.compiler.SpoonFile> files;
        files = new java.util.ArrayList<>();
        for (java.io.File f : file.listFiles()) {
            if (spoon.compiler.SpoonResourceHelper.isFile(f)) {
                files.add(new spoon.support.compiler.FileSystemFile(f));
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
        java.util.List<spoon.compiler.SpoonFolder> subFolders;
        subFolders = new java.util.ArrayList<>();
        for (java.io.File f : file.listFiles()) {
            if (!((spoon.compiler.SpoonResourceHelper.isArchive(f)) || (f.isFile()))) {
                try {
                    subFolders.add(spoon.compiler.SpoonResourceHelper.createFolder(f));
                } catch (java.io.FileNotFoundException e) {
                    spoon.Launcher.LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return subFolders;
    }

    public boolean isFile() {
        return false;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return getPath();
    }

    public java.util.List<spoon.compiler.SpoonFile> getAllJavaFiles() {
        java.util.List<spoon.compiler.SpoonFile> files = new java.util.ArrayList<>();
        for (spoon.compiler.SpoonFile f : getFiles()) {
            if (f.isJava()) {
                files.add(f);
            }
        }
        for (spoon.compiler.SpoonFolder fol : getSubFolders()) {
            files.addAll(fol.getAllJavaFiles());
        }
        return files;
    }

    public java.lang.String getPath() {
        return file.getPath();
    }

    @java.lang.Override
    public boolean isArchive() {
        return false;
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
        if (obj == null) {
            return false;
        }
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
}


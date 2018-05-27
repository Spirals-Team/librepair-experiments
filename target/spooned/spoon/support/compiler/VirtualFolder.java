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


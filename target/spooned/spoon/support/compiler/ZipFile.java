package spoon.support.compiler;


public class ZipFile implements spoon.compiler.SpoonFile {
    byte[] buffer;

    java.lang.String name;

    spoon.support.compiler.ZipFolder parent;

    public ZipFile(spoon.support.compiler.ZipFolder parent, java.lang.String name, byte[] buffer) {
        super();
        this.buffer = buffer;
        this.name = name;
        this.parent = parent;
    }

    public java.io.InputStream getContent() {
        return new java.io.ByteArrayInputStream(buffer);
    }

    public java.lang.String getName() {
        return name;
    }

    public spoon.compiler.SpoonFolder getParent() {
        return parent;
    }

    @java.lang.Override
    public java.io.File getFileSystemParent() {
        return getParent().getFileSystemParent();
    }

    public boolean isFile() {
        return true;
    }

    public boolean isJava() {
        return getName().endsWith(".java");
    }

    public java.lang.String getPath() {
        return toString();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((parent) + "!") + (getName());
    }

    @java.lang.Override
    public boolean isArchive() {
        return true;
    }

    @java.lang.Override
    public java.io.File toFile() {
        return null;
    }

    @java.lang.Override
    public boolean isActualFile() {
        return false;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        return toString().equals(obj.toString());
    }

    @java.lang.Override
    public int hashCode() {
        return toString().hashCode();
    }
}


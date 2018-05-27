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


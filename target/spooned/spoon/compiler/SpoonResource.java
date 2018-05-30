package spoon.compiler;


public interface SpoonResource {
    spoon.compiler.SpoonFolder getParent();

    java.lang.String getName();

    boolean isFile();

    boolean isArchive();

    java.lang.String getPath();

    java.io.File getFileSystemParent();

    java.io.File toFile();
}


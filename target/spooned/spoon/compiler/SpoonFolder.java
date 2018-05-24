package spoon.compiler;


public interface SpoonFolder extends spoon.compiler.SpoonResource {
    java.util.List<spoon.compiler.SpoonFile> getFiles();

    java.util.List<spoon.compiler.SpoonFile> getAllFiles();

    java.util.List<spoon.compiler.SpoonFile> getAllJavaFiles();

    java.util.List<spoon.compiler.SpoonFolder> getSubFolders();

    void addFile(spoon.compiler.SpoonFile source);

    void addFolder(spoon.compiler.SpoonFolder source);
}


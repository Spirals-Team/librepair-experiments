package spoon.compiler;


public interface SpoonFile extends spoon.compiler.SpoonResource {
    java.io.InputStream getContent();

    boolean isJava();

    boolean isActualFile();
}


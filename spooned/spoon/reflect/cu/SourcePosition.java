package spoon.reflect.cu;


public interface SourcePosition extends java.io.Serializable {
    spoon.reflect.cu.SourcePosition NOPOSITION = new spoon.reflect.cu.position.NoSourcePosition();

    boolean isValidPosition();

    java.lang.String toString();

    java.io.File getFile();

    spoon.reflect.cu.CompilationUnit getCompilationUnit();

    int getLine();

    int getEndLine();

    int getColumn();

    int getEndColumn();

    int getSourceEnd();

    int getSourceStart();
}


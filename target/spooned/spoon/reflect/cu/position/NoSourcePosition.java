package spoon.reflect.cu.position;


public class NoSourcePosition implements java.io.Serializable , spoon.reflect.cu.SourcePosition {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public java.io.File getFile() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.cu.CompilationUnit getCompilationUnit() {
        return null;
    }

    @java.lang.Override
    public boolean isValidPosition() {
        return false;
    }

    @java.lang.Override
    public int getLine() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getEndLine() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getColumn() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getEndColumn() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getSourceEnd() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public int getSourceStart() {
        throw new java.lang.UnsupportedOperationException("PartialSourcePosition only contains a CompilationUnit");
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "(unknown file)";
    }
}


package spoon.support.reflect.cu.position;


public class PartialSourcePositionImpl extends spoon.reflect.cu.position.NoSourcePosition {
    private static final long serialVersionUID = 1L;

    private spoon.reflect.cu.CompilationUnit compilationUnit;

    public PartialSourcePositionImpl(spoon.reflect.cu.CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    @java.lang.Override
    public java.io.File getFile() {
        return compilationUnit.getFile();
    }

    @java.lang.Override
    public spoon.reflect.cu.CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
}


package spoon.support.reflect.cu.position;


public class DeclarationSourcePositionImpl extends spoon.support.reflect.cu.position.SourcePositionImpl implements java.io.Serializable , spoon.reflect.cu.position.DeclarationSourcePosition {
    private static final long serialVersionUID = 1L;

    private int modifierSourceEnd;

    private int modifierSourceStart;

    private int declarationSourceStart;

    private int declarationSourceEnd;

    public DeclarationSourcePositionImpl(spoon.reflect.cu.CompilationUnit compilationUnit, int sourceStart, int sourceEnd, int modifierSourceStart, int modifierSourceEnd, int declarationSourceStart, int declarationSourceEnd, int[] lineSeparatorPositions) {
        super(compilationUnit, sourceStart, sourceEnd, lineSeparatorPositions);
        spoon.support.reflect.cu.position.SourcePositionImpl.checkArgsAreAscending(declarationSourceStart, modifierSourceStart, (modifierSourceEnd + 1), sourceStart, (sourceEnd + 1), (declarationSourceEnd + 1));
        this.modifierSourceStart = modifierSourceStart;
        this.declarationSourceStart = declarationSourceStart;
        this.declarationSourceEnd = declarationSourceEnd;
        if ((this.modifierSourceStart) == 0) {
            this.modifierSourceStart = declarationSourceStart;
        }
        this.modifierSourceEnd = modifierSourceEnd;
    }

    @java.lang.Override
    public int getSourceEnd() {
        return declarationSourceEnd;
    }

    @java.lang.Override
    public int getSourceStart() {
        return declarationSourceStart;
    }

    @java.lang.Override
    public int getModifierSourceStart() {
        return modifierSourceStart;
    }

    @java.lang.Override
    public int getNameStart() {
        return super.getSourceStart();
    }

    @java.lang.Override
    public int getNameEnd() {
        return super.getSourceEnd();
    }

    public void setModifierSourceEnd(int modifierSourceEnd) {
        this.modifierSourceEnd = modifierSourceEnd;
    }

    @java.lang.Override
    public int getModifierSourceEnd() {
        return modifierSourceEnd;
    }

    public int getEndLine() {
        return searchLineNumber(declarationSourceEnd);
    }

    @java.lang.Override
    public java.lang.String getSourceDetails() {
        return ((((super.getSourceDetails()) + "\nmodifier = ") + (getFragment(getModifierSourceStart(), getModifierSourceEnd()))) + "\nname = ") + (getFragment(getNameStart(), getNameEnd()));
    }
}


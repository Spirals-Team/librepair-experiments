package spoon.support.reflect.cu.position;


public class SourcePositionImpl implements java.io.Serializable , spoon.reflect.cu.SourcePosition {
    private static final long serialVersionUID = 1L;

    protected int searchLineNumber(int position) {
        if ((lineSeparatorPositions) == null) {
            return 1;
        }
        int length = lineSeparatorPositions.length;
        if (length == 0) {
            return -1;
        }
        int g = 0;
        int d = length - 1;
        int m = 0;
        int start;
        while (g <= d) {
            m = (g + d) / 2;
            if (position < (start = lineSeparatorPositions[m])) {
                d = m - 1;
            }else
                if (position > start) {
                    g = m + 1;
                }else {
                    return m + 1;
                }

        } 
        if (position < (lineSeparatorPositions[m])) {
            return m + 1;
        }
        return m + 2;
    }

    private int searchColumnNumber(int position) {
        if ((lineSeparatorPositions) == null) {
            return -1;
        }
        int length = lineSeparatorPositions.length;
        if (length == 0) {
            return -1;
        }
        int i = 0;
        for (i = 0; i < ((lineSeparatorPositions.length) - 1); i++) {
            if (((lineSeparatorPositions[i]) < position) && ((lineSeparatorPositions[(i + 1)]) > position)) {
                return position - (lineSeparatorPositions[i]);
            }
        }
        int tabCount = 0;
        int tabSize = 0;
        if ((getCompilationUnit()) != null) {
            tabSize = getCompilationUnit().getFactory().getEnvironment().getTabulationSize();
            java.lang.String source = getCompilationUnit().getOriginalSourceCode();
            for (int j = lineSeparatorPositions[i]; j < position; j++) {
                if ((source.charAt(j)) == '\t') {
                    tabCount++;
                }
            }
        }
        return ((position - (lineSeparatorPositions[i])) - tabCount) + (tabCount * tabSize);
    }

    private final int sourceStart;

    private final int sourceEnd;

    private int sourceStartline = -1;

    private final int[] lineSeparatorPositions;

    public SourcePositionImpl(spoon.reflect.cu.CompilationUnit compilationUnit, int sourceStart, int sourceEnd, int[] lineSeparatorPositions) {
        super();
        spoon.support.reflect.cu.position.SourcePositionImpl.checkArgsAreAscending(sourceStart, (sourceEnd + 1));
        this.compilationUnit = compilationUnit;
        this.sourceEnd = sourceEnd;
        this.sourceStart = sourceStart;
        this.lineSeparatorPositions = lineSeparatorPositions;
    }

    @java.lang.Override
    public boolean isValidPosition() {
        return true;
    }

    public int getColumn() {
        return searchColumnNumber(sourceStart);
    }

    public int getEndColumn() {
        return searchColumnNumber(sourceEnd);
    }

    public java.io.File getFile() {
        return (compilationUnit) == null ? null : compilationUnit.getFile();
    }

    public int getLine() {
        if ((sourceStartline) == (-1)) {
            this.sourceStartline = searchLineNumber(this.sourceStart);
        }
        return sourceStartline;
    }

    public int getEndLine() {
        return searchLineNumber(sourceEnd);
    }

    public int getSourceEnd() {
        return this.sourceEnd;
    }

    public int getSourceStart() {
        return this.sourceStart;
    }

    @java.lang.Override
    public java.lang.String toString() {
        if ((getFile()) == null) {
            return "(unknown file)";
        }
        int ln = getLine();
        return ln >= 1 ? ((("(" + (getFile().getAbsolutePath().replace('\\', '/').replace("C:/", "/"))) + ":") + ln) + ")" : getFile().getAbsolutePath().replace('\\', '/').replace("C:/", "/");
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof spoon.reflect.cu.SourcePosition)) {
            return false;
        }
        spoon.reflect.cu.SourcePosition s = ((spoon.reflect.cu.SourcePosition) (obj));
        return (((getFile()) == null ? (s.getFile()) == null : getFile().equals(s.getFile())) && ((getLine()) == (s.getLine()))) && ((getColumn()) == (s.getColumn()));
    }

    @java.lang.Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (getLine());
        result = (prime * result) + (getColumn());
        result = (prime * result) + ((getFile()) != null ? getFile().hashCode() : 1);
        return result;
    }

    private final spoon.reflect.cu.CompilationUnit compilationUnit;

    public spoon.reflect.cu.CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public java.lang.String getSourceDetails() {
        return getFragment(getSourceStart(), getSourceEnd());
    }

    protected java.lang.String getFragment(int start, int end) {
        return ((((("|" + start) + ";") + end) + "|") + (getCompilationUnit().getOriginalSourceCode().substring(start, (end + 1)))) + "|";
    }

    protected static void checkArgsAreAscending(int... values) {
        int last = -1;
        for (int value : values) {
            if (value < 0) {
                throw new spoon.SpoonException("SourcePosition value must not be negative");
            }
            if (last > value) {
                throw new spoon.SpoonException("SourcePosition values must be ascending or equal");
            }
            last = value;
        }
    }
}


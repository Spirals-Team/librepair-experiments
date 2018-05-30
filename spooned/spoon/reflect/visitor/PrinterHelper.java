package spoon.reflect.visitor;


public class PrinterHelper {
    private java.lang.String lineSeparator = java.lang.System.getProperty("line.separator");

    private spoon.compiler.Environment env;

    protected final java.lang.StringBuffer sbf = new java.lang.StringBuffer();

    private int nbTabs = 0;

    private int line = 1;

    private int column = 1;

    private java.util.Map<java.lang.Integer, java.lang.Integer> lineNumberMapping = new java.util.HashMap<>();

    protected boolean shouldWriteTabs = true;

    private boolean lastCharWasCR = false;

    public PrinterHelper(spoon.compiler.Environment env) {
        this.env = env;
    }

    public void reset() {
        sbf.setLength(0);
        nbTabs = 0;
        line = 1;
        column = 1;
        shouldWriteTabs = true;
        lineNumberMapping = new java.util.HashMap<>();
    }

    public spoon.reflect.visitor.PrinterHelper write(java.lang.String s) {
        if (s != null) {
            int len = s.length();
            for (int i = 0; i < len; i++) {
                write(s.charAt(i));
            }
        }
        return this;
    }

    public spoon.reflect.visitor.PrinterHelper write(char c) {
        if (c == '\r') {
            sbf.append(c);
            (line)++;
            column = 1;
            shouldWriteTabs = true;
            lastCharWasCR = true;
            return this;
        }
        if (c == '\n') {
            sbf.append(c);
            if (lastCharWasCR) {
            }else {
                (line)++;
                column = 1;
                shouldWriteTabs = true;
            }
            lastCharWasCR = false;
            return this;
        }
        autoWriteTabs();
        sbf.append(c);
        column += 1;
        lastCharWasCR = false;
        return this;
    }

    public spoon.reflect.visitor.PrinterHelper writeln() {
        write(lineSeparator);
        return this;
    }

    @java.lang.Deprecated
    public spoon.reflect.visitor.PrinterHelper writeTabs() {
        return this;
    }

    private void writeTabsInternal() {
        for (int i = 0; i < (nbTabs); i++) {
            if (env.isUsingTabulations()) {
                sbf.append('\t');
                column += 1;
            }else {
                for (int j = 0; j < (env.getTabulationSize()); j++) {
                    sbf.append(' ');
                    column += 1;
                }
            }
        }
    }

    protected void autoWriteTabs() {
        if (shouldWriteTabs) {
            writeTabsInternal();
            shouldWriteTabs = false;
        }
    }

    public spoon.reflect.visitor.PrinterHelper incTab() {
        (nbTabs)++;
        return this;
    }

    public spoon.reflect.visitor.PrinterHelper decTab() {
        (nbTabs)--;
        return this;
    }

    public int getTabCount() {
        return nbTabs;
    }

    public spoon.reflect.visitor.PrinterHelper setTabCount(int tabCount) {
        nbTabs = tabCount;
        return this;
    }

    public boolean removeLine() {
        java.lang.String ls = lineSeparator;
        int i = (sbf.length()) - (ls.length());
        boolean hasWhite = false;
        while ((i > 0) && (!(ls.equals(sbf.substring(i, (i + (ls.length()))))))) {
            if (!(isWhite(sbf.charAt(i)))) {
                return false;
            }
            hasWhite = true;
            i--;
        } 
        if (i <= 0) {
            return false;
        }
        hasWhite = hasWhite || (isWhite(sbf.charAt((i - 1))));
        sbf.replace(i, (i + (ls.length())), (hasWhite ? "" : " "));
        (line)--;
        return true;
    }

    private boolean isWhite(char c) {
        return (((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r');
    }

    public spoon.reflect.visitor.PrinterHelper adjustStartPosition(spoon.reflect.declaration.CtElement e) {
        if ((!(e.isImplicit())) && (e.getPosition().isValidPosition())) {
            while ((line) < (e.getPosition().getLine())) {
                writeln();
            } 
            while ((line) > (e.getPosition().getLine())) {
                if (!(removeLine())) {
                    break;
                }
            } 
        }
        return this;
    }

    public spoon.reflect.visitor.PrinterHelper adjustEndPosition(spoon.reflect.declaration.CtElement e) {
        if ((env.isPreserveLineNumbers()) && (e.getPosition().isValidPosition())) {
            while ((line) < (e.getPosition().getEndLine())) {
                writeln();
            } 
        }
        return this;
    }

    public void undefineLine() {
        if ((lineNumberMapping.get(line)) == null) {
            putLineNumberMapping(0);
        }
    }

    public void mapLine(spoon.reflect.declaration.CtElement e, spoon.reflect.cu.CompilationUnit unitExpected) {
        spoon.reflect.cu.SourcePosition sp = e.getPosition();
        if (((sp.isValidPosition()) && ((sp.getCompilationUnit()) == unitExpected)) && ((sp instanceof spoon.support.reflect.cu.position.PartialSourcePositionImpl) == false)) {
            putLineNumberMapping(e.getPosition().getLine());
        }else {
            undefineLine();
        }
    }

    public void putLineNumberMapping(int valueLine) {
        lineNumberMapping.put(this.line, valueLine);
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getLineNumberMapping() {
        return java.util.Collections.unmodifiableMap(lineNumberMapping);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return sbf.toString();
    }

    public java.lang.String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(java.lang.String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public void writeSpace() {
        this.write(' ');
    }
}


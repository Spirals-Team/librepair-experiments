package spoon.reflect.visitor;


public class ListPrinter implements java.io.Closeable {
    private final spoon.reflect.visitor.TokenWriter printerTokenWriter;

    private final boolean nextPrefixSpace;

    private final java.lang.String separator;

    private final boolean nextSuffixSpace;

    private final boolean endPrefixSpace;

    private final java.lang.String end;

    private boolean isFirst = true;

    public ListPrinter(spoon.reflect.visitor.TokenWriter printerHelper, boolean startPrefixSpace, java.lang.String start, boolean startSuffixSpace, boolean nextPrefixSpace, java.lang.String next, boolean nextSuffixSpace, boolean endPrefixSpace, java.lang.String end) {
        super();
        this.printerTokenWriter = printerHelper;
        this.nextPrefixSpace = nextPrefixSpace;
        this.separator = next;
        this.nextSuffixSpace = nextSuffixSpace;
        this.endPrefixSpace = endPrefixSpace;
        this.end = end;
        if (startPrefixSpace) {
            printerHelper.writeSpace();
        }
        if ((start != null) && ((start.length()) > 0)) {
            printerTokenWriter.writeSeparator(start);
        }
        if (startSuffixSpace) {
            printerHelper.writeSpace();
        }
    }

    public void printSeparatorIfAppropriate() {
        if (isFirst) {
            isFirst = false;
        }else {
            if (nextPrefixSpace) {
                printerTokenWriter.writeSpace();
            }
            if (((separator) != null) && ((separator.length()) > 0)) {
                printerTokenWriter.writeSeparator(separator);
            }
            if (nextSuffixSpace) {
                printerTokenWriter.writeSpace();
            }
        }
    }

    @java.lang.Override
    public void close() {
        if (endPrefixSpace) {
            printerTokenWriter.writeSpace();
        }
        if (((end) != null) && ((end.length()) > 0)) {
            printerTokenWriter.writeSeparator(end);
        }
    }
}


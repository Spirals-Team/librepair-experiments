package spoon.reflect.visitor.chain;


public enum ScanningMode {
    NORMAL(true,true), SKIP_ALL(false,false), SKIP_CHILDREN(true,false);
    public final boolean visitElement;

    public final boolean visitChildren;

    ScanningMode(boolean visitElement, boolean visitChildren) {
        this.visitElement = visitElement;
        this.visitChildren = visitChildren;
    }
}


package spoon.reflect.visitor.chain;


public interface CtScannerListener {
    spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element);

    void exit(spoon.reflect.declaration.CtElement element);
}


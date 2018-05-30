package spoon.reflect.visitor.printer.change;


class DirectPrinterHelper extends spoon.reflect.visitor.PrinterHelper {
    DirectPrinterHelper(spoon.compiler.Environment env) {
        super(env);
    }

    void directPrint(java.lang.String text) {
        autoWriteTabs();
        sbf.append(text);
    }

    void setShouldWriteTabs(boolean shouldWriteTabs) {
        this.shouldWriteTabs = shouldWriteTabs;
    }
}


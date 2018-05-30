package spoon.support.comparator;


public class SignatureComparator implements java.io.Serializable , java.util.Comparator<spoon.reflect.declaration.CtElement> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public int compare(spoon.reflect.declaration.CtElement o1, spoon.reflect.declaration.CtElement o2) {
        spoon.support.visitor.SignaturePrinter signaturePrinter1 = new spoon.support.visitor.SignaturePrinter();
        spoon.support.visitor.SignaturePrinter signaturePrinter2 = new spoon.support.visitor.SignaturePrinter();
        signaturePrinter1.scan(o1);
        signaturePrinter2.scan(o2);
        return signaturePrinter1.getSignature().compareTo(signaturePrinter2.getSignature());
    }
}


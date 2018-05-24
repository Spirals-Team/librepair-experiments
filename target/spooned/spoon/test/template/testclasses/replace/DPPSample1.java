package spoon.test.template.testclasses.replace;


public class DPPSample1 {
    private spoon.test.template.testclasses.replace.ElementPrinterHelper elementPrinterHelper;

    private spoon.reflect.visitor.TokenWriter printer;

    public <T extends java.lang.Enum<?>> void method1(spoon.reflect.declaration.CtEnum<T> ctEnum) {
        printer.writeSpace().writeKeyword("extends").writeSpace();
        try (spoon.reflect.visitor.ListPrinter lp = elementPrinterHelper.createListPrinter(false, null, false, false, ",", true, false, ";")) {
            for (spoon.reflect.declaration.CtEnumValue<?> enumValue : ctEnum.getEnumValues()) {
                lp.printSeparatorIfAppropriate();
                scan(enumValue);
            }
        }
    }

    public <T extends java.lang.Enum<?>> void method2(spoon.reflect.declaration.CtEnum<T> ctEnum) {
        try (spoon.reflect.visitor.ListPrinter lp = elementPrinterHelper.createListPrinter(false, null, false, false, ",", true, false, ";")) {
            for (spoon.reflect.declaration.CtEnumValue<?> enumValue : ctEnum.getEnumValues()) {
                lp.printSeparatorIfAppropriate();
                scan(enumValue);
            }
        }
    }

    private void scan(spoon.reflect.declaration.CtEnumValue<?> enumValue) {
    }
}


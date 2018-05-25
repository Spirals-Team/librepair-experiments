package spoon.test.jdtimportbuilder.testclasses;


public class StaticImportWithInheritance {
    private boolean b = spoon.test.jdtimportbuilder.testclasses.staticimport.Dependency.TRUE;

    private int n = spoon.test.jdtimportbuilder.testclasses.staticimport.DependencySubClass.OTHER_INT;

    private int value = spoon.test.jdtimportbuilder.testclasses.staticimport.Dependency.maMethod();

    private java.lang.String s = spoon.test.jdtimportbuilder.testclasses.staticimport.Dependency.ANY;
}


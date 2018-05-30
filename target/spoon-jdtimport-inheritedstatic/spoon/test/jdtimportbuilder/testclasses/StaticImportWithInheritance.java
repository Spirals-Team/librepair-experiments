package spoon.test.jdtimportbuilder.testclasses;


import spoon.test.jdtimportbuilder.testclasses.staticimport.Dependency;
import spoon.test.jdtimportbuilder.testclasses.staticimport.DependencySubClass;

import static spoon.test.jdtimportbuilder.testclasses.staticimport.DependencySubClass.*;


public class StaticImportWithInheritance {
    private boolean b = Dependency.TRUE;

    private int n = OTHER_INT;

    private int value = Dependency.maMethod();

    private String s = Dependency.ANY;
}


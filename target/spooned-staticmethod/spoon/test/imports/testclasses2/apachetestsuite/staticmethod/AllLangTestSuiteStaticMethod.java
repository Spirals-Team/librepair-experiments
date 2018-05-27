package spoon.test.imports.testclasses2.apachetestsuite.staticmethod;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import spoon.test.imports.testclasses2.apachetestsuite.LangTestSuite;
import spoon.test.imports.testclasses2.apachetestsuite.enum2.EnumTestSuite;

import static spoon.test.imports.testclasses2.apachetestsuite.enums.EnumTestSuite.suite;


public class AllLangTestSuiteStaticMethod extends TestCase {
    public AllLangTestSuiteStaticMethod(String name) {
        super(name);
    }

    public static void main(String[] args) {
        TestRunner.run(AllLangTestSuiteStaticMethod.truc());
    }

    public static Test truc() {
        TestSuite suite = new TestSuite();
        suite.setName("Commons-Lang (all) Tests");
        suite.addTest(LangTestSuite.suite());
        suite.addTest(EnumTestSuite.suite());
        suite.addTest(suite());
        return suite;
    }
}


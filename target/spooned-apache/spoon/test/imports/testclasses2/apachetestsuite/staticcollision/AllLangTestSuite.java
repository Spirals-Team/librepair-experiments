package spoon.test.imports.testclasses2.apachetestsuite.staticcollision;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import spoon.test.imports.testclasses2.apachetestsuite.LangTestSuite;
import spoon.test.imports.testclasses2.apachetestsuite.enums.EnumTestSuite;


public class AllLangTestSuite extends TestCase {
    public AllLangTestSuite(String name) {
        super(name);
    }

    public static void main(String[] args) {
        TestRunner.run(AllLangTestSuite.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.setName("Commons-Lang (all) Tests");
        suite.addTest(LangTestSuite.suite());
        suite.addTest(EnumTestSuite.suite());
        suite.addTest(spoon.test.imports.testclasses2.apachetestsuite.enum2.EnumTestSuite.suite());
        return suite;
    }
}


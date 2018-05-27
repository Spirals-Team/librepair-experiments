package spoon.test.imports.testclasses2.apachetestsuite.staticjava3;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import spoon.test.imports.testclasses2.apachetestsuite.LangTestSuite;
import spoon.test.imports.testclasses2.apachetestsuite.enum2.EnumTestSuite;


public class AllLangTestJava3 extends TestCase {
    public AllLangTestJava3(String name) {
        super(name);
    }

    public static void main(String[] args) {
        TestRunner.run(AllLangTestJava3.truc());
    }

    public static Test truc() {
        TestSuite suite = new TestSuite();
        suite.setName("Commons-Lang (all) Tests");
        suite.addTest(LangTestSuite.suite());
        suite.addTest(EnumTestSuite.suite());
        suite.addTest(spoon.test.imports.testclasses2.apachetestsuite.enums.EnumTestSuite.suite());
        return suite;
    }
}


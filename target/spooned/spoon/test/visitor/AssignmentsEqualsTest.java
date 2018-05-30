package spoon.test.visitor;


public class AssignmentsEqualsTest {
    @org.junit.Test
    public void testEquals() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/spoon/test/visitor/Assignments.java");
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        java.util.List<spoon.reflect.code.CtAssignment> assignments = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class));
        org.junit.Assert.assertTrue(((assignments.size()) == 10));
        org.junit.Assert.assertFalse(assignments.get(0).equals(assignments.get(1)));
        org.junit.Assert.assertFalse(assignments.get(2).equals(assignments.get(3)));
        org.junit.Assert.assertFalse(assignments.get(4).equals(assignments.get(5)));
        org.junit.Assert.assertFalse(assignments.get(6).equals(assignments.get(7)));
        org.junit.Assert.assertTrue(assignments.get(8).equals(assignments.get(9)));
    }
}


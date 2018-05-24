package spoon.reflect.visitor;


public class CtIteratorTest {
    @org.junit.Test
    public void testCtElementIteration() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtElement root = launcher.getFactory().getModel().getAllTypes().iterator().next();
        testCtIterator(root);
        testAsIterable(root);
        testDescendantIterator(root);
    }

    public void testCtIterator(spoon.reflect.declaration.CtElement root) {
        java.util.Deque<spoon.reflect.declaration.CtElement> ctElements = getDescendantsInDFS(root);
        spoon.reflect.visitor.CtIterator iterator = new spoon.reflect.visitor.CtIterator(root);
        while (iterator.hasNext()) {
            org.junit.Assert.assertEquals(ctElements.pollFirst(), iterator.next());
        } 
    }

    public void testAsIterable(spoon.reflect.declaration.CtElement root) {
        java.util.Deque<spoon.reflect.declaration.CtElement> ctElements = getDescendantsInDFS(root);
        for (spoon.reflect.declaration.CtElement elem : root.asIterable()) {
            org.junit.Assert.assertEquals(elem, ctElements.pollFirst());
        }
    }

    public void testDescendantIterator(spoon.reflect.declaration.CtElement root) {
        java.util.Deque<spoon.reflect.declaration.CtElement> ctElements = getDescendantsInDFS(root);
        root.descendantIterator().forEachRemaining((spoon.reflect.declaration.CtElement elem) -> org.junit.Assert.assertEquals(ctElements.pollFirst(), elem));
    }

    java.util.Deque<spoon.reflect.declaration.CtElement> getDescendantsInDFS(spoon.reflect.declaration.CtElement root) {
        spoon.reflect.visitor.CtIteratorTest.CtScannerList counter = new spoon.reflect.visitor.CtIteratorTest.CtScannerList();
        root.accept(counter);
        return counter.nodes;
    }

    class CtScannerList extends spoon.reflect.visitor.CtScanner {
        public java.util.Deque<spoon.reflect.declaration.CtElement> nodes = new java.util.ArrayDeque<>();

        @java.lang.Override
        protected void enter(spoon.reflect.declaration.CtElement e) {
            nodes.addLast(e);
        }
    }
}


package spoon.test.position;


public class SourceFragmentTest {
    @org.junit.Test
    public void testSourcePositionFragment() throws java.lang.Exception {
        spoon.reflect.cu.SourcePosition sp = new spoon.support.reflect.cu.position.SourcePositionImpl(null, 10, 20, null);
        spoon.reflect.visitor.printer.change.SourceFragment sf = new spoon.reflect.visitor.printer.change.SourceFragment(sp);
        org.junit.Assert.assertEquals(10, sf.getStart());
        org.junit.Assert.assertEquals(21, sf.getEnd());
        org.junit.Assert.assertSame(sp, sf.getSourcePosition());
        org.junit.Assert.assertNull(sf.getFirstChild());
        org.junit.Assert.assertNull(sf.getNextSibling());
    }

    @org.junit.Test
    public void testDeclarationSourcePositionFragment() throws java.lang.Exception {
        spoon.reflect.cu.SourcePosition sp = new spoon.support.reflect.cu.position.DeclarationSourcePositionImpl(null, 100, 110, 90, 95, 90, 130, null);
        spoon.reflect.visitor.printer.change.SourceFragment sf = new spoon.reflect.visitor.printer.change.SourceFragment(sp);
        org.junit.Assert.assertEquals(90, sf.getStart());
        org.junit.Assert.assertEquals(131, sf.getEnd());
        org.junit.Assert.assertSame(sp, sf.getSourcePosition());
        org.junit.Assert.assertNotNull(sf.getFirstChild());
        org.junit.Assert.assertNull(sf.getNextSibling());
        spoon.reflect.visitor.printer.change.SourceFragment sibling;
        sibling = sf.getFirstChild();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, sibling.getFragmentType());
        org.junit.Assert.assertEquals(90, sibling.getStart());
        org.junit.Assert.assertEquals(96, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(96, sibling.getStart());
        org.junit.Assert.assertEquals(100, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(100, sibling.getStart());
        org.junit.Assert.assertEquals(111, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(111, sibling.getStart());
        org.junit.Assert.assertEquals(131, sibling.getEnd());
    }

    @org.junit.Test
    public void testBodyHolderSourcePositionFragment() throws java.lang.Exception {
        spoon.reflect.cu.SourcePosition sp = new spoon.support.reflect.cu.position.BodyHolderSourcePositionImpl(null, 100, 110, 90, 95, 90, 130, 120, 130, null);
        spoon.reflect.visitor.printer.change.SourceFragment sf = new spoon.reflect.visitor.printer.change.SourceFragment(sp);
        org.junit.Assert.assertEquals(90, sf.getStart());
        org.junit.Assert.assertEquals(131, sf.getEnd());
        org.junit.Assert.assertSame(sp, sf.getSourcePosition());
        org.junit.Assert.assertNotNull(sf.getFirstChild());
        org.junit.Assert.assertNull(sf.getNextSibling());
        spoon.reflect.visitor.printer.change.SourceFragment sibling;
        sibling = sf.getFirstChild();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.MODIFIERS, sibling.getFragmentType());
        org.junit.Assert.assertEquals(90, sibling.getStart());
        org.junit.Assert.assertEquals(96, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.BEFORE_NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(96, sibling.getStart());
        org.junit.Assert.assertEquals(100, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(100, sibling.getStart());
        org.junit.Assert.assertEquals(111, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.AFTER_NAME, sibling.getFragmentType());
        org.junit.Assert.assertEquals(111, sibling.getStart());
        org.junit.Assert.assertEquals(120, sibling.getEnd());
        sibling = sibling.getNextSibling();
        org.junit.Assert.assertSame(spoon.reflect.visitor.printer.change.FragmentType.BODY, sibling.getFragmentType());
        org.junit.Assert.assertEquals(120, sibling.getStart());
        org.junit.Assert.assertEquals(131, sibling.getEnd());
    }

    @org.junit.Test
    public void testSourceFragmentAddChild() throws java.lang.Exception {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragment = createFragment(10, 20);
        spoon.reflect.visitor.printer.change.SourceFragment f;
        org.junit.Assert.assertSame(rootFragment, rootFragment.add((f = createFragment(10, 15))));
        org.junit.Assert.assertSame(rootFragment.getFirstChild(), f);
        org.junit.Assert.assertSame(rootFragment, rootFragment.add((f = createFragment(15, 20))));
        org.junit.Assert.assertSame(rootFragment.getFirstChild().getNextSibling(), f);
        org.junit.Assert.assertSame(rootFragment, rootFragment.add((f = createFragment(15, 20))));
        org.junit.Assert.assertSame(rootFragment.getFirstChild().getNextSibling().getFirstChild(), f);
        org.junit.Assert.assertSame(rootFragment, rootFragment.add((f = createFragment(16, 20))));
        org.junit.Assert.assertSame(rootFragment.getFirstChild().getNextSibling().getFirstChild().getFirstChild(), f);
        org.junit.Assert.assertSame(rootFragment, rootFragment.add((f = createFragment(20, 100))));
        org.junit.Assert.assertSame(rootFragment.getNextSibling(), f);
        f = createFragment(5, 10);
        org.junit.Assert.assertSame(f, rootFragment.add(f));
        org.junit.Assert.assertSame(f.getNextSibling(), rootFragment);
    }

    @org.junit.Test
    public void testSourceFragmentWrapChild() throws java.lang.Exception {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragment = createFragment(0, 100);
        spoon.reflect.visitor.printer.change.SourceFragment child = createFragment(50, 60);
        rootFragment.add(child);
        spoon.reflect.visitor.printer.change.SourceFragment childWrapper = createFragment(40, 60);
        rootFragment.add(childWrapper);
        org.junit.Assert.assertSame(rootFragment.getFirstChild(), childWrapper);
        org.junit.Assert.assertSame(rootFragment.getFirstChild().getFirstChild(), child);
    }

    @org.junit.Test
    public void testSourceFragmentWrapChildrenAndSiblings() throws java.lang.Exception {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragment = createFragment(0, 100);
        spoon.reflect.visitor.printer.change.SourceFragment child = createFragment(50, 60);
        rootFragment.add(child);
        spoon.reflect.visitor.printer.change.SourceFragment childWrapper = createFragment(40, 70);
        spoon.reflect.visitor.printer.change.SourceFragment childA = createFragment(40, 50);
        spoon.reflect.visitor.printer.change.SourceFragment childB = createFragment(50, 55);
        spoon.reflect.visitor.printer.change.SourceFragment childC = createFragment(60, 65);
        spoon.reflect.visitor.printer.change.SourceFragment childD = createFragment(65, 70);
        org.junit.Assert.assertSame(childWrapper, childWrapper.add(childA).add(childB).add(childC).add(childD));
        rootFragment.add(childWrapper);
        org.junit.Assert.assertSame(rootFragment.getFirstChild(), childWrapper);
        org.junit.Assert.assertSame(childA, childWrapper.getFirstChild());
        org.junit.Assert.assertSame(child, childA.getNextSibling());
        org.junit.Assert.assertSame(childB, child.getFirstChild());
        org.junit.Assert.assertSame(childC, child.getNextSibling());
        org.junit.Assert.assertSame(childD, childC.getNextSibling());
    }

    @org.junit.Test
    public void testLocalizationOfSourceFragment() throws java.lang.Exception {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragment = createFragment(0, 100);
        spoon.reflect.visitor.printer.change.SourceFragment x;
        rootFragment.add(createFragment(50, 60));
        rootFragment.add(createFragment(60, 70));
        rootFragment.add((x = createFragment(50, 55)));
        org.junit.Assert.assertSame(x, rootFragment.getSourceFragmentOf(50, 55));
        org.junit.Assert.assertSame(rootFragment, rootFragment.getSourceFragmentOf(0, 100));
        org.junit.Assert.assertSame(rootFragment.getFirstChild(), rootFragment.getSourceFragmentOf(50, 60));
        org.junit.Assert.assertSame(rootFragment.getFirstChild().getNextSibling(), rootFragment.getSourceFragmentOf(60, 70));
    }

    private spoon.reflect.visitor.printer.change.SourceFragment createFragment(int start, int end) {
        return new spoon.reflect.visitor.printer.change.SourceFragment(new spoon.support.reflect.cu.position.SourcePositionImpl(null, start, (end - 1), null));
    }
}


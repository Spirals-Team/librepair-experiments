package spoon.test.ctElement;


import spoon.Launcher;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.declaration.CtAnnotationImpl;
import spoon.support.reflect.declaration.CtMethodImpl;


public class ElementTest {
    @org.junit.Test
    public void testGetFactory() {
        Launcher spoon = new Launcher();
        CtElement element = spoon.getFactory().createAnnotation();
        org.junit.Assert.assertNotNull(element.getFactory());
        CtElement otherElement = new CtAnnotationImpl<>();
        org.junit.Assert.assertNotNull(otherElement.getFactory());
        CtElement yetAnotherOne = new CtMethodImpl<>();
        org.junit.Assert.assertNotNull(yetAnotherOne.getFactory());
        org.junit.Assert.assertSame(otherElement.getFactory(), yetAnotherOne.getFactory());
    }
}


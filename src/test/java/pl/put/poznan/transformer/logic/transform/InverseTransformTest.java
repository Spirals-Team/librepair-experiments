package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;
import pl.put.poznan.transformer.logic.TextTransformer;

import static org.junit.Assert.*;

public class InverseTransformTest {
    private Transform transform;
    private InverseTransform inverseTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.inverseTransform = new InverseTransform(transform);
    }
    @Test
    public void testTransformationReverseOne() {
        assertEquals("Txet elpmas si Siht", inverseTransform.apply("This is sample Text"));
        assertEquals("Muspi Merol Muspi Merol", inverseTransform.apply("Lorem Ipsum Lorem Ipsum"));
        assertEquals("Tsket ywOmoDnAr owopyt tsej ot", inverseTransform.apply("To jest TypOwO randomowy tekst"));
        assertEquals("!tuo toor !tUo stHgil", inverseTransform.apply("Lights out! Root Out!"));
    }


}
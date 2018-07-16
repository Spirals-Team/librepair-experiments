package eu.stamp_project.dspot.amplifier;

import eu.stamp_project.AbstractTest;
import eu.stamp_project.Utils;
import eu.stamp_project.utils.AmplificationHelper;
import org.junit.Test;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringLiteralAmplifierTest extends AbstractTest {

    @Test
    public void testFlatString() throws Exception {
        final CtClass testClass = Utils.findClass("fr.inria.ampl.ToBeAmplifiedLiteralTest");
        CtMethod<?> method = Utils.findMethod(testClass, "testInt");

        StringLiteralAmplifier.flatStringLiterals(method);
        System.out.println(method);

        // TODO spoon adds parenthesis and flat literals does not work in specific case, e.g.
        // TODO java.lang.String s3 = (s1 + "hey") + "ho"; while the source is written as
        // TODO String s3 = s1 + "hey" + "ho";
    }

    @Test
    public void testAmplify() throws Exception {

        /*
            test the StringLiteral
            The first iteration is complete, i.e. apply random operations plus the specific strings
         */

        final String nameMethod = "methodString";
        CtClass<Object> literalMutationClass = Utils.getFactory().Class().get("fr.inria.amp.LiteralMutation");
        AmplificationHelper.setSeedRandom(42L);
        Amplifier amplifier = new StringLiteralAmplifier();
        amplifier.reset(literalMutationClass);
        CtMethod method = literalMutationClass.getMethod(nameMethod);
        List<CtMethod> mutantMethods = amplifier.apply(method).collect(Collectors.toList());
        assertEquals(43, mutantMethods.size());
    }

    @Test
    public void testDoesNotAmplifyChar() throws Exception {
        final String nameMethod = "methodCharacter";
        CtClass<Object> literalMutationClass = Utils.getFactory().Class().get("fr.inria.amp.LiteralMutation");
        AmplificationHelper.setSeedRandom(42L);
        Amplifier mutator = new StringLiteralAmplifier();
        mutator.reset(literalMutationClass);
        CtMethod method = literalMutationClass.getMethod(nameMethod);
        List<CtMethod> mutantMethods = mutator.apply(method).collect(Collectors.toList());
        assertTrue(mutantMethods.isEmpty());
    }

    @Test
    public void testFlattenString() throws Exception {
        CtClass<Object> literalMutationClass = Utils.getFactory().Class().get("fr.inria.amp.JavaPoet");
        final CtMethod withConcat = Utils.findMethod(literalMutationClass, "method");
        StringLiteralAmplifier.flatStringLiterals(withConcat);
        System.out.println(withConcat);
    }
}

package eu.stamp_project.dspot.amplifier;

import eu.stamp_project.AbstractTest;
import eu.stamp_project.Utils;
import eu.stamp_project.utils.AmplificationHelper;
import org.junit.Test;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Benjamin DANGLOT
 * benjamin.danglot@inria.fr
 * on 13/07/18
 */
public class LiteralAmplifiersTest extends AbstractTest {

    @Test
    public void test() throws Exception {

        /*
            This test the application of multiple amplifier, multiple time
            The multiple applications of amplifiers should result with non-redundant amplified test
            Here, we test that amplifiers marks amplified nodes and do not amplify them again
            This avoid redundant transformation,
            and thus improve the global performance in term of memory and execution time of DSpot
         */

        final String nameMethod = "methodString";
        CtClass<?> literalMutationClass = Utils.getFactory().Class().get("fr.inria.amp.LiteralMutation");
        AmplificationHelper.setSeedRandom(42L);
        Amplifier stringLiteralAmplifier = new StringLiteralAmplifier();
        stringLiteralAmplifier.reset(literalMutationClass);
        Amplifier numberLiteralAmplifier= new NumberLiteralAmplifier();
        numberLiteralAmplifier.reset(literalMutationClass);
        final CtMethod method = Utils.findMethod(literalMutationClass, nameMethod);

        // 1rst application of both amplifiers
        Stream<CtMethod<?>> amplifiedStringMethods = stringLiteralAmplifier.apply(method);
        Stream<CtMethod<?>> amplifiedNumberMethods = numberLiteralAmplifier.apply(method);

        final List<CtMethod<?>> amplifiedMethods = Stream.concat(amplifiedNumberMethods, amplifiedStringMethods).collect(Collectors.toList());
        assertEquals(28, amplifiedMethods.size());

        // 2nd application of both amplifiers:
        amplifiedStringMethods = amplifiedMethods.stream().flatMap(stringLiteralAmplifier::apply);
        amplifiedNumberMethods = amplifiedMethods.stream().flatMap(numberLiteralAmplifier::apply);
        //here, we have less amplified test method than before from more than 1k to 366
        assertEquals(366, Stream.concat(amplifiedStringMethods, amplifiedNumberMethods).count());
    }

    @Test
    public void testAvoidRedundantAmplification() throws Exception {

        /*
            This test implements the example cases showed in https://github.com/STAMP-project/dspot/issues/454
         */

        CtClass<?> literalMutationClass = Utils.getFactory().Class().get("fr.inria.amp.LiteralMutation");
        final String nameMethod = "methodString";
        final CtMethod method = Utils.findMethod(literalMutationClass, nameMethod);
        final CtMethod clone = method.clone();
        clone.setSimpleName("temporaryMethod");
        clone.setBody(Utils.getFactory().createCodeSnippetStatement("int x = 1 + 1").compile());
        Amplifier zeroAmplifier = new AbstractLiteralAmplifier<Integer>() {
            @Override
            protected Set<CtLiteral<Integer>> amplify(CtLiteral<Integer> original, CtMethod<?> testMethod) {
                return Collections.singleton(testMethod.getFactory().createLiteral(0));
            }
            @Override
            protected String getSuffix() {
                return "zero-amplifier";
            }
            @Override
            protected Class<?> getTargetedClass() {
                return Integer.class;
            }
        };
        literalMutationClass.addMethod(clone);

        // used to verify that the application of Amplifiers does not modify the given test method
        final String originalTestMethodString = clone.toString();

        List<CtMethod<?>> zeroAmplifiedTests = zeroAmplifier.apply(clone).collect(Collectors.toList());
        assertEquals(2, zeroAmplifiedTests.size());
        assertEquals(originalTestMethodString, clone.toString()); // the original test method has not been modified
        zeroAmplifiedTests = zeroAmplifiedTests.stream().flatMap(zeroAmplifier::apply).collect(Collectors.toList());
        assertEquals(originalTestMethodString, clone.toString());
        assertEquals(1, zeroAmplifiedTests.size());
        literalMutationClass.removeMethod(clone);
    }
}

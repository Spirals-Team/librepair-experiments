package spoon.testing;


public class AbstractAssertTest {
    public static final java.lang.String PATH = "./src/test/java/spoon/testing/testclasses/";

    @org.junit.Test
    public void testTransformationWithProcessorInstantiated() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(((spoon.testing.AbstractAssertTest.PATH) + "Foo.java")).withProcessor(new spoon.testing.processors.FooToBarProcessor()).isEqualTo(((spoon.testing.AbstractAssertTest.PATH) + "Bar.java"));
    }

    @org.junit.Test
    public void testTransformationWithProcessorClass() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(((spoon.testing.AbstractAssertTest.PATH) + "Foo.java")).withProcessor(spoon.testing.processors.FooToBarProcessor.class).isEqualTo(((spoon.testing.AbstractAssertTest.PATH) + "Bar.java"));
    }

    @org.junit.Test
    public void testTransformationWithProcessorName() throws java.lang.Exception {
        spoon.testing.Assert.assertThat(((spoon.testing.AbstractAssertTest.PATH) + "Foo.java")).withProcessor(spoon.testing.processors.FooToBarProcessor.class.getName()).isEqualTo(((spoon.testing.AbstractAssertTest.PATH) + "Bar.java"));
    }

    @org.junit.Test
    public void testTransformationFromCtElementWithProcessor() throws java.lang.Exception {
        class MyProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtField<?>> {
            @java.lang.Override
            public void process(spoon.reflect.declaration.CtField<?> element) {
                element.setSimpleName("j");
            }
        }
        final spoon.reflect.declaration.CtType<spoon.testing.CtElementAssertTest> type = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.testing.CtElementAssertTest.class).Type().get(spoon.testing.CtElementAssertTest.class);
        spoon.testing.Assert.assertThat(type.getField("i")).withProcessor(new MyProcessor()).isEqualTo("public int j;");
    }
}


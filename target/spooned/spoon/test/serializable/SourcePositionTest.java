package spoon.test.serializable;


public class SourcePositionTest {
    private static spoon.reflect.factory.Factory loadFactory(java.io.File file) throws java.io.IOException {
        return new spoon.support.SerializationModelStreamer().load(new java.io.FileInputStream(file));
    }

    private static void saveFactory(spoon.reflect.factory.Factory factory, java.io.File file) throws java.io.IOException {
        java.io.ByteArrayOutputStream outstr = new java.io.ByteArrayOutputStream();
        new spoon.support.SerializationModelStreamer().save(factory, outstr);
        java.io.OutputStream fileStream = new java.io.FileOutputStream(file);
        outstr.writeTo(fileStream);
    }

    @org.junit.Test
    public void testSourcePosition() throws java.io.IOException {
        java.io.File modelFile = new java.io.File("./src/test/resources/serialization/model");
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/serialization/SomeClass.java");
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.test.serializable.SourcePositionTest.saveFactory(factory, modelFile);
        spoon.reflect.factory.Factory factoryFromFile = spoon.test.serializable.SourcePositionTest.loadFactory(modelFile);
        spoon.reflect.declaration.CtType<?> type = factory.Type().get("SomeClass");
        spoon.reflect.declaration.CtType<?> typeFromFile = factoryFromFile.Type().get("SomeClass");
        org.junit.Assert.assertTrue(type.getPosition().getFile().equals(typeFromFile.getPosition().getFile()));
        org.junit.Assert.assertTrue(((type.getPosition().getLine()) == (typeFromFile.getPosition().getLine())));
        org.junit.Assert.assertTrue(((type.getPosition().getColumn()) == (typeFromFile.getPosition().getColumn())));
        spoon.reflect.declaration.CtField<?> elem1 = type.getField("a");
        spoon.reflect.declaration.CtField<?> elem2 = typeFromFile.getField("a");
        org.junit.Assert.assertTrue(elem1.getPosition().getFile().equals(elem2.getPosition().getFile()));
    }
}


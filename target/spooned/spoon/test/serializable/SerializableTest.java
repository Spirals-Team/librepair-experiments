package spoon.test.serializable;


public class SerializableTest {
    @org.junit.Test
    public void testSerialCtStatement() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        spoon.reflect.code.CtStatement sta2 = factory.Code().createCodeSnippetStatement("String hello =\"t1\"; System.out.println(hello)").compile();
        byte[] ser = spoon.support.util.ByteSerialization.serialize(sta2);
        spoon.reflect.code.CtStatement des = ((spoon.reflect.code.CtStatement) (spoon.support.util.ByteSerialization.deserialize(ser)));
        java.lang.String sigBef = sta2.getShortRepresentation();
        java.lang.String sigAf = des.getShortRepresentation();
        spoon.reflect.declaration.CtType<?> typeBef = sta2.getParent(spoon.reflect.declaration.CtType.class);
        org.junit.Assert.assertNotNull(typeBef);
        org.junit.Assert.assertEquals(sigBef, sigAf);
        des.setFactory(factory);
        java.lang.String toSBef = sta2.toString();
        java.lang.String toSgAf = des.toString();
        org.junit.Assert.assertEquals(toSBef, toSgAf);
        spoon.reflect.declaration.CtType<?> typeDes = des.getParent(spoon.reflect.declaration.CtType.class);
        org.junit.Assert.assertNotNull(typeDes);
        spoon.reflect.declaration.CtType<?> decl = typeDes.getDeclaringType();
        org.junit.Assert.assertNull(decl);
        spoon.reflect.declaration.CtPackage parentOriginal = ((spoon.reflect.declaration.CtPackage) (typeBef.getParent()));
        spoon.reflect.declaration.CtPackage parentDeser = ((spoon.reflect.declaration.CtPackage) (typeDes.getParent()));
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME, parentOriginal.getSimpleName());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME, parentDeser.getSimpleName());
    }

    @org.junit.Test
    public void testSerialFile() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.serializable", "Dummy");
        byte[] ser = spoon.support.util.ByteSerialization.serialize(type);
        spoon.reflect.declaration.CtType<?> des = ((spoon.reflect.declaration.CtType<?>) (spoon.support.util.ByteSerialization.deserialize(ser)));
    }

    @org.junit.Test
    public void testSerializationModelStreamer() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build("spoon.test.serializable", "Dummy").getFactory();
        java.io.ByteArrayOutputStream outstr = new java.io.ByteArrayOutputStream();
        new spoon.support.SerializationModelStreamer().save(factory, outstr);
        spoon.reflect.factory.Factory loadedFactory = new spoon.support.SerializationModelStreamer().load(new java.io.ByteArrayInputStream(outstr.toByteArray()));
        org.junit.Assert.assertFalse(factory.Type().getAll().isEmpty());
        org.junit.Assert.assertFalse(loadedFactory.Type().getAll().isEmpty());
        org.junit.Assert.assertEquals(factory.getModel().getRootPackage(), loadedFactory.getModel().getRootPackage());
        for (spoon.reflect.declaration.CtType type : loadedFactory.Type().getAll()) {
            org.junit.Assert.assertSame(loadedFactory, type.getFactory());
            org.junit.Assert.assertSame(loadedFactory, type.getPosition().getCompilationUnit().getFactory());
        }
    }
}


package spoon.test.reflect.visitor;


public class ReferenceQueryTest {
    @org.junit.Test
    public void getAllTypeReferencesInEnum() throws java.lang.Exception {
        spoon.reflect.declaration.CtEnum<spoon.test.reflect.visitor.ReferenceQueryTestEnum> testEnum = spoon.testing.utils.ModelUtils.build("spoon.test.reflect.visitor", "ReferenceQueryTestEnum");
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> enumTypeRefs = spoon.reflect.visitor.Query.getElements(testEnum, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class));
        spoon.reflect.factory.TypeFactory typeFactory = testEnum.getFactory().Type();
        for (java.lang.Class<?> c : new java.lang.Class<?>[]{ java.lang.Integer.class, java.lang.Long.class, java.lang.Boolean.class, java.lang.Number.class, java.lang.String.class, java.lang.Void.class }) {
            org.junit.Assert.assertTrue("the reference query on the enum should return all the types defined in the enum declaration", enumTypeRefs.contains(typeFactory.createReference(c)));
        }
    }
}


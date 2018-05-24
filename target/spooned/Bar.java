

public class Bar {
    private <T extends spoon.reflect.declaration.CtElement> T get(Tacos<T> elemType) {
        spoon.reflect.declaration.CtClass<java.lang.Object> fooClass = factory.Class().get(spoon.test.parent.Foo.class);
        spoon.reflect.declaration.CtMethod nullParent = fooClass.getMethodsByName("nullParent").get(0);
        return ((T) (nullParent.getBody().getElements(elemType::isInstance).get(0)));
    }
}


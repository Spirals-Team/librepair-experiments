package spoon.test.template.testclasses.inheritance;


public class InterfaceTemplate extends spoon.template.ExtensionTemplate implements java.io.Serializable , spoon.test.template.testclasses.inheritance.A , spoon.test.template.testclasses.inheritance.B {
    @spoon.template.Parameter
    public spoon.reflect.reference.CtTypeReference A;

    @spoon.template.Parameter
    public java.lang.Class B = java.rmi.Remote.class;

    private final spoon.reflect.factory.Factory factory;

    public InterfaceTemplate(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        A = getFactory().Type().createReference(java.lang.Comparable.class);
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }
}


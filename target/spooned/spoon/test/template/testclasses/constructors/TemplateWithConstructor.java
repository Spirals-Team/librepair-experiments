package spoon.test.template.testclasses.constructors;


public class TemplateWithConstructor extends spoon.template.ExtensionTemplate {
    @spoon.template.Parameter
    spoon.reflect.reference.CtTypeReference<?> _Interf_;

    @spoon.template.Local
    public TemplateWithConstructor(spoon.reflect.reference.CtTypeReference<?> interf) {
        super();
        _Interf_ = interf;
    }

    public TemplateWithConstructor(java.lang.String arg) {
        super();
        java.lang.System.out.println("new");
    }

    public TemplateWithConstructor(int arg) {
        super();
        java.lang.System.out.println("new");
    }

    java.util.List<spoon.test.template.testclasses.constructors._Interf_> toBeInserted = new java.util.ArrayList<spoon.test.template.testclasses.constructors._Interf_>();
}


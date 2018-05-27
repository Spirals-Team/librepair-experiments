package spoon.test.template.testclasses;


public class InvocationTemplate extends spoon.template.ExtensionTemplate {
    spoon.test.template.testclasses.InvocationTemplate.IFace iface;

    void invoke() {
        iface.$method$();
    }

    @spoon.template.Local
    public InvocationTemplate(spoon.reflect.reference.CtTypeReference<?> ifaceType, java.lang.String methodName) {
        this._IFace = ifaceType.getSimpleName();
        this.$method$ = methodName;
    }

    @spoon.template.Parameter("IFace")
    java.lang.String _IFace;

    @spoon.template.Parameter
    java.lang.String $method$;

    @spoon.template.Local
    interface IFace {
        void $method$();
    }
}


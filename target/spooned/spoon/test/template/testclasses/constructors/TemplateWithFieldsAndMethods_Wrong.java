package spoon.test.template.testclasses.constructors;


public class TemplateWithFieldsAndMethods_Wrong extends spoon.template.ExtensionTemplate {
    @spoon.template.Parameter
    public java.lang.String PARAM;

    @spoon.template.Local
    public TemplateWithFieldsAndMethods_Wrong(java.lang.String PARAM) {
        this.PARAM = PARAM;
    }

    public java.lang.String methodToBeInserted() {
        return PARAM;
    }
}


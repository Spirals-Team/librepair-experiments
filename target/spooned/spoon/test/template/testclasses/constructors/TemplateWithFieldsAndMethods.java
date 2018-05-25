package spoon.test.template.testclasses.constructors;


public class TemplateWithFieldsAndMethods extends spoon.template.ExtensionTemplate {
    @spoon.template.Parameter
    public java.lang.String PARAM;

    public spoon.template.TemplateParameter<java.lang.String> PARAM2;

    @spoon.template.Local
    public TemplateWithFieldsAndMethods(java.lang.String PARAM, spoon.template.TemplateParameter<java.lang.String> PARAM2) {
        this.PARAM = PARAM;
        this.PARAM2 = PARAM2;
    }

    public java.lang.String methodToBeInserted() {
        return "PARAM";
    }

    public java.lang.String fieldToBeInserted;

    public java.lang.String methodToBeInserted2() {
        return PARAM2.S();
    }
}


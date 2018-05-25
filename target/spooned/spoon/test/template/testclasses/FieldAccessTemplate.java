package spoon.test.template.testclasses;


public class FieldAccessTemplate extends spoon.template.ExtensionTemplate {
    int $field$;

    void m() {
        $field$ = 7;
    }

    @spoon.template.Local
    public FieldAccessTemplate(java.lang.String fieldName) {
        this.fieldName = fieldName;
    }

    @spoon.template.Parameter("$field$")
    java.lang.String fieldName;
}


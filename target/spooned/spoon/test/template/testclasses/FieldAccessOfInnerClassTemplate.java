package spoon.test.template.testclasses;


public class FieldAccessOfInnerClassTemplate extends spoon.template.ExtensionTemplate {
    class Inner {
        int $field$;

        void m() {
            $field$ = 7;
        }
    }

    @spoon.template.Local
    public FieldAccessOfInnerClassTemplate(java.lang.String fieldName) {
        this.$field$ = fieldName;
    }

    @spoon.template.Parameter
    java.lang.String $field$;
}


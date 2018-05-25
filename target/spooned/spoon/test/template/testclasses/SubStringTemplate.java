package spoon.test.template.testclasses;


public class SubStringTemplate extends spoon.template.ExtensionTemplate {
    java.lang.String m_$name$ = "$name$ is here more times: $name$";

    void set$name$(java.lang.String p_$name$) {
        this.m_$name$ = p_$name$;
    }

    void m() {
        set$name$("The $name$ is here too");
    }

    @spoon.template.Parameter
    java.lang.Object $name$;

    @spoon.template.Local
    public SubStringTemplate(java.lang.Object o) {
        this.$name$ = o;
    }
}


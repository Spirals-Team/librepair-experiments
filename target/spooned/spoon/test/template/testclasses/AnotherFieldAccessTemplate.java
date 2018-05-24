package spoon.test.template.testclasses;


public class AnotherFieldAccessTemplate extends spoon.template.ExtensionTemplate {
    @spoon.template.Parameter("$name$")
    java.lang.String name = "x";

    int $name$;

    int m_$name$;

    {
        java.lang.System.out.println((($name$) + (m_$name$)));
    }
}


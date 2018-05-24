package spoon.test.template.testclasses;


public class ClassAccessTemplate extends spoon.template.StatementTemplate {
    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        aClass.getName();
    }

    @spoon.template.Parameter
    java.lang.Class aClass;

    @spoon.template.Local
    public ClassAccessTemplate(java.lang.Class aClass) {
        this.aClass = aClass;
    }

    @spoon.template.Local
    class _AClass_ {}
}


package spoon.test.template.testclasses;


public class SubstituteArrayAccessTemplate extends spoon.template.StatementTemplate {
    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        anArray.toString();
    }

    @spoon.template.Parameter
    java.lang.String[] anArray;

    @spoon.template.Local
    public SubstituteArrayAccessTemplate(java.lang.String[] anArray) {
        this.anArray = anArray;
    }
}


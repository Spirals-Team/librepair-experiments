package spoon.test.template.testclasses;


public class SubstituteArrayLengthTemplate extends spoon.template.StatementTemplate {
    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        if ((anArray.length) > 0);
    }

    @spoon.template.Parameter
    java.lang.String[] anArray;

    @spoon.template.Local
    public SubstituteArrayLengthTemplate(java.lang.String[] anArray) {
        this.anArray = anArray;
    }
}


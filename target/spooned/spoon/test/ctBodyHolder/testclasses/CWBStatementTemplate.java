package spoon.test.ctBodyHolder.testclasses;


public class CWBStatementTemplate extends spoon.template.StatementTemplate {
    @spoon.template.Parameter
    java.lang.String value;

    public CWBStatementTemplate(java.lang.String val) {
        value = val;
    }

    @java.lang.Override
    public void statement() throws java.lang.Throwable {
        java.lang.System.out.println(value);
    }
}


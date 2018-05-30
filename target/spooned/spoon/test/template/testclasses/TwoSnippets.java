package spoon.test.template.testclasses;


public class TwoSnippets {
    private java.lang.String bDao;

    private spoon.test.template.testclasses.ContextHelper contextHelper;

    public void hello() {
        if (!(contextHelper.hasPermission("c"))) {
            throw new java.lang.SecurityException();
        }
        bDao.toString();
    }

    public void toto() {
        if (!(contextHelper.hasPermission("c"))) {
            throw new java.lang.SecurityException();
        }
        bDao.toString();
    }
}


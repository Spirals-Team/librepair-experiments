package spoon.test.template.testclasses;


public class BServiceImpl {
    private java.lang.String bDao;

    private spoon.test.template.testclasses.ContextHelper contextHelper;

    public void hello() {
        if (!(contextHelper.hasPermission("c"))) {
            throw new java.lang.SecurityException();
        }
        bDao.toString();
    }
}


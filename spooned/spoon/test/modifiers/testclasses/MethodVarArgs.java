package spoon.test.modifiers.testclasses;


public class MethodVarArgs {
    protected java.lang.Object[] getInitValues(java.lang.String contentClassName, java.lang.String... initNames) throws java.io.IOException {
        for (java.lang.String s : initNames) {
            java.io.File f = new java.io.File(s);
            if (f.getAbsolutePath().equals(contentClassName)) {
                return f.listFiles();
            }
        }
        return null;
    }
}


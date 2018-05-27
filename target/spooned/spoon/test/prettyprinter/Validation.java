package spoon.test.prettyprinter;


public class Validation {
    private static final java.lang.String[] invalidIdentifiers = new java.lang.String[]{ "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while" };

    private static final boolean IS_SECURITY_ENABLED = (java.lang.System.getSecurityManager()) != null;

    private static final boolean SKIP_IDENTIFIER_CHECK;

    static {
        if (spoon.test.prettyprinter.Validation.IS_SECURITY_ENABLED) {
            SKIP_IDENTIFIER_CHECK = java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<java.lang.Boolean>() {
                @java.lang.Override
                public java.lang.Boolean run() {
                    return java.lang.Boolean.valueOf(java.lang.System.getProperty("org.apache.el.parser.SKIP_IDENTIFIER_CHECK", "false"));
                }
            }).booleanValue();
        }else {
            SKIP_IDENTIFIER_CHECK = java.lang.Boolean.valueOf(java.lang.System.getProperty("org.apache.el.parser.SKIP_IDENTIFIER_CHECK", "false")).booleanValue();
        }
    }

    private Validation() {
    }

    public static boolean isIdentifier(@java.lang.Deprecated
    java.lang.String key) {
        if (spoon.test.prettyprinter.Validation.SKIP_IDENTIFIER_CHECK) {
            return true;
        }
        if ((key == null) || ((key.length()) == 0)) {
            return false;
        }
        int i = 0;
        int j = spoon.test.prettyprinter.Validation.invalidIdentifiers.length;
        while (i < j) {
            int k = (i + j) >>> 1;
            int result = spoon.test.prettyprinter.Validation.invalidIdentifiers[k].compareTo(key);
            if (result == 0) {
                return false;
            }
            if (result < 0) {
                i = k + 1;
            }else {
                j = k;
            }
        } 
        if (!(java.lang.Character.isJavaIdentifierStart(key.charAt(0)))) {
            return false;
        }
        for (int idx = 1; idx < (key.length()); idx++) {
            if (!(java.lang.Character.isJavaIdentifierPart(key.charAt(idx)))) {
                return false;
            }
        }
        return true;
    }
}


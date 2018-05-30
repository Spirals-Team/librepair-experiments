package spoon.reflect.visitor;


public enum JavaIdentifiers {
    IMPORT, PACKAGE, INTERFACE, CLASS, ENUM, SUPER, THIS, ASSERT, EXTENDS, BREAK, CASE, CATCH, CONTINUE, DO, FOR, IF, NEW, RETURN, SWITCH, THROW, TRY, WHILE, PUBLIC, PROTECTED, PRIVATE, ABSTRACT, STATIC, FINAL, TRANSIENT, VOLATILE, SYNCHRONIZED, NATIVE, STRICTFP;
    public static java.util.Set<java.lang.String> KEYWORDS;

    static {
        KEYWORDS = new java.util.HashSet<>();
        for (spoon.reflect.visitor.JavaIdentifiers kw : java.util.Arrays.asList(spoon.reflect.visitor.JavaIdentifiers.values())) {
            spoon.reflect.visitor.JavaIdentifiers.KEYWORDS.add(kw.name().toLowerCase());
        }
    }

    static boolean isJavaIdentifier(java.lang.String s) {
        if (((s.length()) == 0) || (!(java.lang.Character.isJavaIdentifierStart(s.charAt(0))))) {
            return false;
        }
        for (int i = 1; i < (s.length()); i++) {
            if (!(java.lang.Character.isJavaIdentifierPart(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLegalJavaIdentifier(java.lang.String string) {
        if (string == null) {
            return false;
        }
        return (!(spoon.reflect.visitor.JavaIdentifiers.KEYWORDS.contains(string))) && (spoon.reflect.visitor.JavaIdentifiers.isJavaIdentifier(string));
    }

    public static boolean isLegalJavaPackageIdentifier(java.lang.String string) {
        if (string == null) {
            return false;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(string, ".");
        while (st.hasMoreElements()) {
            java.lang.String s = st.nextToken();
            if (!(spoon.reflect.visitor.JavaIdentifiers.isLegalJavaIdentifier(s))) {
                return false;
            }
        } 
        return true;
    }

    public static boolean isLegalJavaExecutableIdentifier(java.lang.String string) {
        if (string == null) {
            return false;
        }
        if (string.equals(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME)) {
            return true;
        }
        return spoon.reflect.visitor.JavaIdentifiers.isLegalJavaIdentifier(string);
    }
}


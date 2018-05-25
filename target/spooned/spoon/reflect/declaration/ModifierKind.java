package spoon.reflect.declaration;


public enum ModifierKind {
    PUBLIC, PROTECTED, PRIVATE, ABSTRACT, STATIC, FINAL, TRANSIENT, VOLATILE, SYNCHRONIZED, NATIVE, STRICTFP;
    private java.lang.String lowercase = null;

    @java.lang.Override
    public java.lang.String toString() {
        if ((lowercase) == null) {
            lowercase = name().toLowerCase(java.util.Locale.US);
        }
        return lowercase;
    }
}


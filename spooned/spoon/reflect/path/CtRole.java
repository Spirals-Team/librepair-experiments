package spoon.reflect.path;


public enum CtRole {
    NAME, TYPE, MULTI_TYPE, DECLARING_TYPE, CONTAINED_TYPE, BODY, IS_SHADOW, BOUND, BOUNDING_TYPE, IS_FINAL, IS_STATIC, IS_UPPER, IS_IMPLICIT, IS_DEFAULT, IS_VARARGS, DEFAULT_EXPRESSION, THEN, ELSE, PACKAGE_REF, SUB_PACKAGE, CONDITION, RIGHT_OPERAND, LEFT_OPERAND, LABEL, CASE, OPERATOR_KIND, PARAMETER, ARGUMENT_TYPE, EXPRESSION, TARGET, VARIABLE, FINALIZER, THROWN, ASSIGNMENT, ASSIGNED, MODIFIER, COMMENT, ANNOTATION_TYPE, INTERFACE, ANNOTATION, STATEMENT, ARGUMENT, SUPER_TYPE, TYPE_MEMBER, NESTED_TYPE(spoon.reflect.path.CtRole.TYPE_MEMBER), CONSTRUCTOR(spoon.reflect.path.CtRole.TYPE_MEMBER), METHOD(spoon.reflect.path.CtRole.TYPE_MEMBER), ANNONYMOUS_EXECUTABLE(spoon.reflect.path.CtRole.TYPE_MEMBER), FIELD(spoon.reflect.path.CtRole.TYPE_MEMBER), EXECUTABLE_REF, CAST, VALUE, FOR_UPDATE, FOR_INIT, FOREACH_VARIABLE, TRY_RESOURCE, DIMENSION, CATCH, TARGET_LABEL, TYPE_PARAMETER, TYPE_ARGUMENT, COMMENT_TAG, COMMENT_CONTENT, COMMENT_TYPE, DOCUMENTATION_TYPE, JAVADOC_TAG_VALUE, POSITION, SNIPPET, ACCESSED_TYPE, IMPORT_REFERENCE, MODULE_DIRECTIVE, REQUIRED_MODULE(spoon.reflect.path.CtRole.MODULE_DIRECTIVE), MODULE_REF, EXPORTED_PACKAGE(spoon.reflect.path.CtRole.MODULE_DIRECTIVE), OPENED_PACKAGE(spoon.reflect.path.CtRole.MODULE_DIRECTIVE), SERVICE_TYPE(spoon.reflect.path.CtRole.MODULE_DIRECTIVE), IMPLEMENTATION_TYPE, PROVIDED_SERVICE(spoon.reflect.path.CtRole.MODULE_DIRECTIVE);
    private final spoon.reflect.path.CtRole superRole;

    private final java.util.List<spoon.reflect.path.CtRole> subRoles;

    private java.util.List<spoon.reflect.path.CtRole> initSubRoles;

    CtRole() {
        this(null);
    }

    CtRole(spoon.reflect.path.CtRole superRole) {
        this.superRole = superRole;
        this.initSubRoles = new java.util.ArrayList<>(0);
        this.subRoles = java.util.Collections.unmodifiableList(this.initSubRoles);
        if (superRole != null) {
            superRole.initSubRoles.add(this);
        }
    }

    static {
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            role.initSubRoles = null;
        }
    }

    public static spoon.reflect.path.CtRole fromName(java.lang.String name) {
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            if ((role.getCamelCaseName().toLowerCase().equals(name.toLowerCase())) || (role.name().equals(name))) {
                return role;
            }
        }
        return null;
    }

    public java.lang.String getCamelCaseName() {
        java.lang.String s = name().toLowerCase();
        java.lang.String[] tokens = s.split("_");
        if ((tokens.length) == 1) {
            return s;
        }else {
            java.lang.StringBuilder buffer = new java.lang.StringBuilder(tokens[0]);
            for (int i = 1; i < (tokens.length); i++) {
                java.lang.String t = tokens[i];
                buffer.append(java.lang.Character.toUpperCase(t.charAt(0)));
                buffer.append(t.substring(1));
            }
            return buffer.toString();
        }
    }

    @java.lang.Override
    public java.lang.String toString() {
        return getCamelCaseName();
    }

    public spoon.reflect.path.CtRole getSuperRole() {
        return superRole;
    }

    public java.util.List<spoon.reflect.path.CtRole> getSubRoles() {
        return subRoles;
    }
}


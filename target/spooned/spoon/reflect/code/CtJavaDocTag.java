package spoon.reflect.code;


public interface CtJavaDocTag extends spoon.reflect.declaration.CtElement {
    java.lang.String JAVADOC_TAG_PREFIX = "@";

    enum TagType {
        AUTHOR, DEPRECATED, EXCEPTION, PARAM, RETURN, SEE, SERIAL, SERIAL_DATA, SERIAL_FIELD, SINCE, THROWS, VERSION, UNKNOWN;
        public boolean hasParam() {
            return ((this) == (spoon.reflect.code.CtJavaDocTag.TagType.PARAM)) || ((this) == (spoon.reflect.code.CtJavaDocTag.TagType.THROWS));
        }

        public static spoon.reflect.code.CtJavaDocTag.TagType tagFromName(java.lang.String tagName) {
            for (spoon.reflect.code.CtJavaDocTag.TagType t : spoon.reflect.code.CtJavaDocTag.TagType.values()) {
                if (t.name().toLowerCase().equals(tagName.toLowerCase())) {
                    return t;
                }
            }
            return spoon.reflect.code.CtJavaDocTag.TagType.UNKNOWN;
        }

        @java.lang.Override
        public java.lang.String toString() {
            return (spoon.reflect.code.CtJavaDocTag.JAVADOC_TAG_PREFIX) + (name().toLowerCase());
        }
    }

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    spoon.reflect.code.CtJavaDocTag.TagType getType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setType(java.lang.String type);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setType(spoon.reflect.code.CtJavaDocTag.TagType type);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    java.lang.String getContent();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    <E extends spoon.reflect.code.CtJavaDocTag> E setContent(java.lang.String content);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE)
    java.lang.String getParam();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setParam(java.lang.String param);

    @java.lang.Override
    spoon.reflect.code.CtJavaDocTag clone();
}


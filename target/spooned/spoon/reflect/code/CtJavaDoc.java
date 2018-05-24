package spoon.reflect.code;


public interface CtJavaDoc extends spoon.reflect.code.CtComment {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    java.util.List<spoon.reflect.code.CtJavaDocTag> getTags();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E setTags(java.util.List<spoon.reflect.code.CtJavaDocTag> tags);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E addTag(spoon.reflect.code.CtJavaDocTag tag);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E addTag(int index, spoon.reflect.code.CtJavaDocTag tag);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E removeTag(int index);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E removeTag(spoon.reflect.code.CtJavaDocTag tag);

    @spoon.support.DerivedProperty
    java.lang.String getShortDescription();

    @spoon.support.DerivedProperty
    java.lang.String getLongDescription();

    @java.lang.Override
    spoon.reflect.code.CtJavaDoc clone();
}


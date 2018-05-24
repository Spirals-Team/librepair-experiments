package spoon.support.reflect.code;


public class CtJavaDocTagImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.code.CtJavaDocTag {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    private spoon.reflect.code.CtJavaDocTag.TagType type;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    private java.lang.String content;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE)
    private java.lang.String param;

    @java.lang.Override
    public spoon.reflect.code.CtJavaDocTag.TagType getType() {
        return type;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDocTag> E setType(java.lang.String type) {
        this.setType(spoon.reflect.code.CtJavaDocTag.TagType.tagFromName(type));
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDocTag> E setType(spoon.reflect.code.CtJavaDocTag.TagType type) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DOCUMENTATION_TYPE, type, this.type);
        this.type = type;
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.String getContent() {
        return content;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDocTag> E setContent(java.lang.String content) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.COMMENT_CONTENT, content, this.content);
        this.content = content;
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.String getParam() {
        return param;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDocTag> E setParam(java.lang.String param) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE, param, this.param);
        this.param = param;
        return ((E) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtJavaDocTag(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtJavaDocTag clone() {
        return ((spoon.reflect.code.CtJavaDocTag) (super.clone()));
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((((this.getType().toString()) + " ") + (this.param)) + (java.lang.System.lineSeparator())) + "\t\t") + (this.content)) + (java.lang.System.lineSeparator());
    }
}


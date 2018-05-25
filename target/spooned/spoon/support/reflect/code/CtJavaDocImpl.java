package spoon.support.reflect.code;


public class CtJavaDocImpl extends spoon.support.reflect.code.CtCommentImpl implements spoon.reflect.code.CtJavaDoc {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    java.util.List<spoon.reflect.code.CtJavaDocTag> tags = new java.util.ArrayList<>();

    public CtJavaDocImpl() {
        super(spoon.reflect.code.CtComment.CommentType.JAVADOC);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtJavaDocTag> getTags() {
        return new java.util.ArrayList<>(tags);
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E setTags(java.util.List<spoon.reflect.code.CtJavaDocTag> tags) {
        if (tags == null) {
            return ((E) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.COMMENT_TAG, this.tags, new java.util.ArrayList<>(this.tags));
        this.tags = new java.util.ArrayList<>();
        for (spoon.reflect.code.CtJavaDocTag tag : tags) {
            this.addTag(tag);
        }
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E addTag(spoon.reflect.code.CtJavaDocTag tag) {
        if (tag != null) {
            tag.setParent(this);
            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.COMMENT_TAG, tags, tag);
            tags.add(tag);
        }
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E addTag(int index, spoon.reflect.code.CtJavaDocTag tag) {
        tag.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.COMMENT_TAG, tags, index, tag);
        tags.add(index, tag);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E removeTag(int index) {
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.COMMENT_TAG, tags, index, tags.get(index));
        tags.remove(index);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E removeTag(spoon.reflect.code.CtJavaDocTag tag) {
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.COMMENT_TAG, tags, tags.indexOf(tag), tag);
        tags.remove(tag);
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.String getShortDescription() {
        int indexEndSentence = this.getContent().indexOf(".");
        if (indexEndSentence == (-1)) {
            indexEndSentence = this.getContent().indexOf("\n");
        }
        if (indexEndSentence != (-1)) {
            return this.getContent().substring(0, (indexEndSentence + 1)).trim();
        }else {
            return this.getContent().trim();
        }
    }

    @java.lang.Override
    public java.lang.String getLongDescription() {
        int indexStartLongDescription = getShortDescription().length();
        if (indexStartLongDescription < (this.getContent().trim().length())) {
            return this.getContent().substring(indexStartLongDescription).trim();
        }else {
            return this.getContent().trim();
        }
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtJavaDoc(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtJavaDoc clone() {
        return ((spoon.reflect.code.CtJavaDoc) (super.clone()));
    }
}


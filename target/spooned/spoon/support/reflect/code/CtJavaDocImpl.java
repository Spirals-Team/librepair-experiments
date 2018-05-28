package spoon.support.reflect.code;


public class CtJavaDocImpl extends spoon.support.reflect.code.CtCommentImpl implements spoon.reflect.code.CtJavaDoc {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    private final spoon.support.util.ModelList<spoon.reflect.code.CtJavaDocTag> tags = new spoon.support.util.ModelList<spoon.reflect.code.CtJavaDocTag>() {
        @java.lang.Override
        protected spoon.reflect.declaration.CtElement getOwner() {
            return spoon.support.reflect.code.CtJavaDocImpl.this;
        }

        @java.lang.Override
        protected spoon.reflect.path.CtRole getRole() {
            return spoon.reflect.path.CtRole.COMMENT_TAG;
        }

        @java.lang.Override
        protected int getDefaultCapacity() {
            return 2;
        }
    };

    public CtJavaDocImpl() {
        super(spoon.reflect.code.CtComment.CommentType.JAVADOC);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtJavaDocTag> getTags() {
        return tags;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E setTags(java.util.List<spoon.reflect.code.CtJavaDocTag> tags) {
        this.tags.set(tags);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E addTag(spoon.reflect.code.CtJavaDocTag tag) {
        this.tags.add(tag);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E addTag(int index, spoon.reflect.code.CtJavaDocTag tag) {
        this.tags.add(index, tag);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E removeTag(int index) {
        this.tags.remove(index);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtJavaDoc> E removeTag(spoon.reflect.code.CtJavaDocTag tag) {
        this.tags.remove(tag);
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


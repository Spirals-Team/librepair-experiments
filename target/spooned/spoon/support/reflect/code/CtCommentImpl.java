package spoon.support.reflect.code;


public class CtCommentImpl extends spoon.support.reflect.code.CtStatementImpl implements spoon.reflect.code.CtComment {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    private java.lang.String content;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT_TYPE)
    private spoon.reflect.code.CtComment.CommentType type;

    public CtCommentImpl() {
    }

    protected CtCommentImpl(spoon.reflect.code.CtComment.CommentType type) {
        this.type = type;
    }

    @java.lang.Override
    public java.lang.String getContent() {
        return content;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtComment> E setContent(java.lang.String content) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.COMMENT_CONTENT, content, this.content);
        this.content = content;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtComment.CommentType getCommentType() {
        return type;
    }

    @java.lang.Override
    public <E extends spoon.reflect.code.CtComment> E setCommentType(spoon.reflect.code.CtComment.CommentType commentType) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TYPE, commentType, this.type);
        type = commentType;
        return ((E) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtComment(this);
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        if ((o == null) || ((getClass()) != (o.getClass()))) {
            return false;
        }
        spoon.support.reflect.code.CtCommentImpl ctComment = ((spoon.support.reflect.code.CtCommentImpl) (o));
        if ((content) != null ? !(content.equals(ctComment.content)) : (ctComment.content) != null) {
            return false;
        }
        return (type) == (ctComment.type);
    }

    @java.lang.Override
    public int hashCode() {
        int result = super.hashCode();
        result = (31 * result) + ((content) != null ? content.hashCode() : 0);
        result = (31 * result) + ((type) != null ? type.hashCode() : 0);
        return result;
    }

    @java.lang.Override
    public spoon.reflect.code.CtComment clone() {
        return ((spoon.reflect.code.CtComment) (super.clone()));
    }

    @java.lang.Override
    public spoon.reflect.code.CtJavaDoc asJavaDoc() {
        if ((this) instanceof spoon.reflect.code.CtJavaDoc) {
            return ((spoon.reflect.code.CtJavaDoc) (this));
        }
        throw new java.lang.IllegalStateException("not a javadoc comment");
    }
}


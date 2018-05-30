package spoon.reflect.code;


public interface CtComment extends spoon.reflect.code.CtStatement {
    enum CommentType {
        FILE, JAVADOC, INLINE, BLOCK;}

    java.lang.String LINE_SEPARATOR = "\n";

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    java.lang.String getContent();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    <E extends spoon.reflect.code.CtComment> E setContent(java.lang.String content);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_TYPE)
    spoon.reflect.code.CtComment.CommentType getCommentType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TYPE)
    <E extends spoon.reflect.code.CtComment> E setCommentType(spoon.reflect.code.CtComment.CommentType commentType);

    @java.lang.Override
    spoon.reflect.code.CtComment clone();

    spoon.reflect.code.CtJavaDoc asJavaDoc();
}


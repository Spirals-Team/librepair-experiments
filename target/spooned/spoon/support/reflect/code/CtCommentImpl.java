/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
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

    /**
     * The comments are not printed during the CtElement equality.
     * The method is this overridden for CtComment.
     */
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


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
package spoon.reflect.code;


/**
 * This code element defines a comment
 *
 * Example:
 * <code>
 * int x = 0;
 * // a comment
 * </code>
 */
public interface CtComment extends spoon.reflect.code.CtStatement {
    enum CommentType {
        FILE, JAVADOC, INLINE, BLOCK;}

    /**
     * This line separator is used in comments returned by {@link #getContent()}.
     * It is OS independent.
     * It has no influence to pretty printed comments, which uses by default OS dependent line separator
     */
    java.lang.String LINE_SEPARATOR = "\n";

    /**
     * Get the content of the comment
     *
     * @return the content of the comment
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    java.lang.String getContent();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    <E extends spoon.reflect.code.CtComment> E setContent(java.lang.String content);

    /**
     * Get the type of the comment
     *
     * @return the comment type
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_TYPE)
    spoon.reflect.code.CtComment.CommentType getCommentType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TYPE)
    <E extends spoon.reflect.code.CtComment> E setCommentType(spoon.reflect.code.CtComment.CommentType commentType);

    @java.lang.Override
    spoon.reflect.code.CtComment clone();

    /**
     * Utility method to for casting the object, throws an exception if not of the correct type
     */
    spoon.reflect.code.CtJavaDoc asJavaDoc();
}


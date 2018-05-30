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
 * This code element defines a javadoc comment
 *
 * Example:
 * <pre>
 * &#x2F;**
 *  * Description
 *  * @tag a tag in the javadoc
 * *&#x2F;
 * </pre>
 */
public interface CtJavaDoc extends spoon.reflect.code.CtComment {
    /**
     * Get all the tag of the javadoc
     *
     * @return the tag list
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    java.util.List<spoon.reflect.code.CtJavaDocTag> getTags();

    /**
     * Define the list of tags
     *
     * @param tags
     * 		the new list of tags
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E setTags(java.util.List<spoon.reflect.code.CtJavaDocTag> tags);

    /**
     * Add a new tag at the end of the list
     *
     * @param tag
     * 		the new tag
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E addTag(spoon.reflect.code.CtJavaDocTag tag);

    /**
     * Add a new tag at the index position
     *
     * @param index
     * 		the index of the new tag
     * @param tag
     * 		the new tag
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E addTag(int index, spoon.reflect.code.CtJavaDocTag tag);

    /**
     * Remove a tag from the index
     *
     * @param index
     * 		the position of the tag to remove
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E removeTag(int index);

    /**
     * Remove a specific tag
     *
     * @param tag
     * 		the tag to remove
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_TAG)
    <E extends spoon.reflect.code.CtJavaDoc> E removeTag(spoon.reflect.code.CtJavaDocTag tag);

    /**
     * Get the short summary of the javadoc (first sentence of the javadoc)
     *
     * @return the summary of the javadoc
     */
    @spoon.support.DerivedProperty
    java.lang.String getShortDescription();

    /**
     * Get the long description of the javadoc
     *
     * @return the long description of the javadoc
     */
    @spoon.support.DerivedProperty
    java.lang.String getLongDescription();

    @java.lang.Override
    spoon.reflect.code.CtJavaDoc clone();
}


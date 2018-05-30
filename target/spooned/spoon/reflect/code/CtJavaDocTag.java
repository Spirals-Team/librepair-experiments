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
 * This code element defines a javadoc tag
 *
 * Example:
 * <code>
 *
 * @since name description
 * </code>
 */
public interface CtJavaDocTag extends spoon.reflect.declaration.CtElement {
    /**
     * The tag prefix
     */
    java.lang.String JAVADOC_TAG_PREFIX = "@";

    /**
     * Define the possible type for a tag
     */
    enum TagType {
        AUTHOR, DEPRECATED, EXCEPTION, PARAM, RETURN, SEE, SERIAL, SERIAL_DATA, SERIAL_FIELD, SINCE, THROWS, VERSION, UNKNOWN;
        /**
         * Return true if the tag can have a parameter
         *
         * @return true if the tag can have a parameter
         */
        public boolean hasParam() {
            return ((this) == (spoon.reflect.code.CtJavaDocTag.TagType.PARAM)) || ((this) == (spoon.reflect.code.CtJavaDocTag.TagType.THROWS));
        }

        /**
         * Get the tag type associated to a name
         *
         * @param tagName
         * 		the tag name
         * @return the tag type
         */
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

    /**
     * The type of the tag
     *
     * @return the type of the tag
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    spoon.reflect.code.CtJavaDocTag.TagType getType();

    /**
     * Define the type of the tag
     *
     * @param type
     * 		the type name
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setType(java.lang.String type);

    /**
     * Define the type of the tag
     *
     * @param type
     * 		the new type
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DOCUMENTATION_TYPE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setType(spoon.reflect.code.CtJavaDocTag.TagType type);

    /**
     * Get the content of the atg
     *
     * @return the content of the tag
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    java.lang.String getContent();

    /**
     * Define the content of the tag
     *
     * @param content
     * 		the new content of the tag
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT_CONTENT)
    <E extends spoon.reflect.code.CtJavaDocTag> E setContent(java.lang.String content);

    /**
     * Get the parameter of the tag return null when none is specified (only for @param and @throws)
     *
     * @return the parameter
     */
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE)
    java.lang.String getParam();

    /**
     * Define a parameter
     *
     * @param param
     * 		the parameter
     */
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE)
    <E extends spoon.reflect.code.CtJavaDocTag> E setParam(java.lang.String param);

    @java.lang.Override
    spoon.reflect.code.CtJavaDocTag clone();
}


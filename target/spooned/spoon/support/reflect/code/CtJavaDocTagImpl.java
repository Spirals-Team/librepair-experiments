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
        return ((((((this.getType().toString()) + " ")// Space required between tag type and parameter
         + (this.param)) + (java.lang.System.lineSeparator()))// Tag parameter
         + "\t\t") + (this.content)) + (java.lang.System.lineSeparator());
    }
}


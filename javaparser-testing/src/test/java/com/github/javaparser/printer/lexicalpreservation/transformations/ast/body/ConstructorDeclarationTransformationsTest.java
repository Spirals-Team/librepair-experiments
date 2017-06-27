/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser.printer.lexicalpreservation.transformations.ast.body;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.printer.lexicalpreservation.AbstractLexicalPreservingTest;
import org.junit.Test;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Transforming ConstructorDeclaration and verifying the LexicalPreservation works as expected.
 */
public class ConstructorDeclarationTransformationsTest extends AbstractLexicalPreservingTest {

    protected ConstructorDeclaration consider(String code) {
        considerCode("class A { " + code + " }");
        return (ConstructorDeclaration) cu.getType(0).getMembers().get(0);
    }

    // Name

    @Test
    public void settingName() throws IOException {
        ConstructorDeclaration cd = consider("A(){}");
        cd.setName("B");
        assertTransformedToString("B(){}", cd);
    }

    // JavaDoc

    // Modifiers

    @Test
    public void addingModifiers() throws IOException {
        ConstructorDeclaration cd = consider("A(){}");
        cd.setModifiers(EnumSet.of(Modifier.PUBLIC));
        assertTransformedToString("public A(){}", cd);
    }

    @Test
    public void removingModifiers() throws IOException {
        ConstructorDeclaration cd = consider("public A(){}");
        cd.setModifiers(EnumSet.noneOf(Modifier.class));
        assertTransformedToString("A(){}", cd);
    }

    @Test
    public void replacingModifiers() throws IOException {
        ConstructorDeclaration cd = consider("public A(){}");
        cd.setModifiers(EnumSet.of(Modifier.PROTECTED));
        assertTransformedToString("protected A(){}", cd);
    }

    // Parameters

    @Test
    public void addingParameters() throws IOException {
        ConstructorDeclaration cd = consider("A(){}");
        cd.addParameter(PrimitiveType.doubleType(), "d");
        assertTransformedToString("A(double d){}", cd);
    }

    @Test
    public void removingOnlyParameter() throws IOException {
        ConstructorDeclaration cd = consider("public A(double d){}");
        cd.getParameters().remove(0);
        assertTransformedToString("public A(){}", cd);
    }

    @Test
    public void removingFirstParameterOfMany() throws IOException {
        ConstructorDeclaration cd = consider("public A(double d, float f){}");
        cd.getParameters().remove(0);
        assertTransformedToString("public A(float f){}", cd);
    }

    @Test
    public void removingLastParameterOfMany() throws IOException {
        ConstructorDeclaration cd = consider("public A(double d, float f){}");
        cd.getParameters().remove(1);
        assertTransformedToString("public A(double d){}", cd);
    }

    @Test
    public void replacingOnlyParameter() throws IOException {
        ConstructorDeclaration cd = consider("public A(float f){}");
        cd.getParameters().set(0, new Parameter(new ArrayType(PrimitiveType.intType()), new SimpleName("foo")));
        assertTransformedToString("public A(int[] foo){}", cd);
    }

    // ThrownExceptions

    // Body

    // Annotations
}

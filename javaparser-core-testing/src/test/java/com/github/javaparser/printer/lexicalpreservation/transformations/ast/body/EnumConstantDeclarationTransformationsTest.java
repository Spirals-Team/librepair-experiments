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

import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.printer.lexicalpreservation.AbstractLexicalPreservingTest;
import org.junit.Test;

import java.io.IOException;

/**
 * Transforming EnumConstantDeclaration and verifying the LexicalPreservation works as expected.
 */
public class EnumConstantDeclarationTransformationsTest extends AbstractLexicalPreservingTest {

    protected EnumConstantDeclaration consider(String code) {
        considerCode("enum A { " + code + " }");
        return cu.getType(0).asEnumDeclaration().getEntries().get(0);
    }

    // Name

    @Test
    public void settingName() {
        EnumConstantDeclaration ecd = consider("A");
        ecd.setName("B");
        assertTransformedToString("B", ecd);
    }

    // Annotations

    // Javadoc

}

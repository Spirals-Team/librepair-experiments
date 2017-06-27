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

package com.github.javaparser.ast.imports;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImportDeclarationTest {
    @Test
    public void singleTypeImportDeclaration() {
        ImportDeclaration i = JavaParser.parseImport("import a.b.c.X;");
        assertEquals("a.b.c.X", i.getNameAsString());
    }

    @Test
    public void typeImportOnDemandDeclaration() {
        ImportDeclaration i = JavaParser.parseImport("import a.b.c.D.*;");
        assertEquals("a.b.c.D", i.getName().toString());
        assertEquals("D", i.getName().getIdentifier());
    }

    @Test
    public void singleStaticImportDeclaration() {
        ImportDeclaration i = JavaParser.parseImport("import static a.b.c.X.def;");
        assertEquals("a.b.c.X", i.getName().getQualifier().get().asString());
        assertEquals("def", i.getName().getIdentifier());
    }

    @Test
    public void staticImportOnDemandDeclaration() {
        ImportDeclaration i = JavaParser.parseImport("import static a.b.c.X.*;");
        assertEquals("a.b.c.X", i.getNameAsString());
    }

}
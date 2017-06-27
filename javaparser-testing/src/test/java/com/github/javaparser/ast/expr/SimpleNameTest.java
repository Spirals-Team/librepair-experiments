/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2017 The JavaParser Team.
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

package com.github.javaparser.ast.expr;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SimpleNameTest {

    @Test
    public void defaultConstructorSetsIdentifierToEmpty() {
        assertEquals("empty", new SimpleName().getIdentifier());
    }

    @Test(expected = AssertionError.class)
    public void identifierMustNotBeEmpty() {
        new SimpleName("");
    }

    @Test(expected = AssertionError.class)
    public void identifierMustNotBeNull() {
        new SimpleName(null);
    }

}
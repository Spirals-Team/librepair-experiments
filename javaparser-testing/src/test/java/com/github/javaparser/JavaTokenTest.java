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

package com.github.javaparser;

import com.github.javaparser.ast.expr.Expression;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static com.github.javaparser.GeneratedJavaParserConstants.*;
import static com.github.javaparser.Providers.provider;
import static com.github.javaparser.Range.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaTokenTest {

    @Test
    public void testAFewTokens() {
        ParseResult<Expression> result = new JavaParser().parse(ParseStart.EXPRESSION, provider("1 +/*2*/1 "));
        List<JavaToken> tokens = result.getTokens().get();
        Iterator<JavaToken> iterator = tokens.iterator();
        assertToken("1", range(1, 1, 1, 1), INTEGER_LITERAL, iterator.next());
        assertToken(" ", range(1, 2, 1, 2), SPACE, iterator.next());
        assertToken("+", range(1, 3, 1, 3), PLUS, iterator.next());
        assertToken("/*2*/", range(1, 4, 1, 8), MULTI_LINE_COMMENT, iterator.next());
        assertToken("1", range(1, 9, 1, 9), INTEGER_LITERAL, iterator.next());
        assertToken(" ", range(1, 10, 1, 10), SPACE, iterator.next());
        assertToken("", range(1, 10, 1, 10), EOF, iterator.next());
        assertEquals(false, iterator.hasNext());
    }

    private void assertToken(String image, Range range, int kind, JavaToken token) {
        assertEquals(image, token.getText());
        assertEquals(range, token.getRange());
        assertEquals(kind, token.getKind());
        token.getNextToken().ifPresent(nt -> assertEquals(token, nt.getPreviousToken().get()));
        token.getPreviousToken().ifPresent(pt -> assertEquals(token, pt.getNextToken().get()));
        assertTrue(token.getNextToken().isPresent() || token.getPreviousToken().isPresent());
    }
}

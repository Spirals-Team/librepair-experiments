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

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.Type;
import org.junit.Test;

import java.util.Optional;

import static com.github.javaparser.ParseStart.*;
import static com.github.javaparser.Range.*;
import static com.github.javaparser.utils.TestUtils.assertInstanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaParserTest {

    @Test
    public void rangeOfAnnotationMemberDeclarationIsCorrect() {
        String code = "@interface AD { String foo(); }";
        CompilationUnit cu = JavaParser.parse(code);
        AnnotationMemberDeclaration memberDeclaration = (AnnotationMemberDeclaration) cu.getAnnotationDeclarationByName("AD").get().getMember(0);
        assertEquals(true, memberDeclaration.getRange().isPresent());
        assertEquals(new Range(new Position(1, 17), new Position(1, 29)), memberDeclaration.getRange().get());
    }

    @Test
    public void rangeOfAnnotationMemberDeclarationWithArrayTypeIsCorrect() {
        String code = "@interface AD { String[] foo(); }";
        CompilationUnit cu = JavaParser.parse(code);
        AnnotationMemberDeclaration memberDeclaration = (AnnotationMemberDeclaration) cu.getAnnotationDeclarationByName("AD").get().getMember(0);
        assertEquals(true, memberDeclaration.getRange().isPresent());
        assertEquals(new Range(new Position(1, 17), new Position(1, 31)), memberDeclaration.getRange().get());
    }

    @Test
    public void rangeOfArrayCreationLevelWithExpressionIsCorrect() {
        String code = "new int[123][456]";
        ArrayCreationExpr expression = JavaParser.parseExpression(code);
        Optional<Range> range;

        range = expression.getLevels().get(0).getRange();
        assertEquals(true, range.isPresent());
        assertEquals(new Range(new Position(1, 8), new Position(1, 12)), range.get());

        range = expression.getLevels().get(1).getRange();
        assertEquals(true, range.isPresent());
        assertEquals(new Range(new Position(1, 13), new Position(1, 17)), range.get());
    }

    @Test
    public void rangeOfArrayCreationLevelWithoutExpressionIsCorrect() {
        String code = "new int[][]";
        ArrayCreationExpr expression = JavaParser.parseExpression(code);
        Optional<Range> range;

        range = expression.getLevels().get(0).getRange();
        assertEquals(true, range.isPresent());
        assertEquals(new Range(new Position(1, 8), new Position(1, 9)), range.get());

        range = expression.getLevels().get(1).getRange();
        assertEquals(true, range.isPresent());
        assertEquals(new Range(new Position(1, 10), new Position(1, 11)), range.get());
    }

    @Test
    public void parseErrorContainsLocation() {
        ParseResult<CompilationUnit> result = new JavaParser().parse(COMPILATION_UNIT, Providers.provider("class X { // blah"));

        Problem problem = result.getProblem(0);
        assertEquals(range(1, 9, 1, 17), problem.getLocation().get().toRange());
        assertEquals("Parse error. Found <EOF>, expected one of  \";\" \"<\" \"@\" \"abstract\" \"boolean\" \"byte\" \"char\" \"class\" \"default\" \"double\" \"enum\" \"exports\" \"final\" \"float\" \"int\" \"interface\" \"long\" \"module\" \"native\" \"open\" \"opens\" \"private\" \"protected\" \"provides\" \"public\" \"requires\" \"short\" \"static\" \"strictfp\" \"synchronized\" \"to\" \"transient\" \"transitive\" \"uses\" \"void\" \"volatile\" \"with\" \"{\" \"}\" <IDENTIFIER>", problem.getMessage());
        assertInstanceOf(ParseException.class, problem.getCause().get());
    }

    @Test
    public void parseIntersectionType() {
        String code = "(Runnable & Serializable) (() -> {})";
        Expression expression = JavaParser.parseExpression(code);
        Type type = ((CastExpr)expression).getType();

        assertTrue(type instanceof IntersectionType);
        IntersectionType intersectionType = (IntersectionType)type;
        assertEquals(2, intersectionType.getElements().size());
        assertTrue(intersectionType.getElements().get(0) instanceof ClassOrInterfaceType);
        assertEquals("Runnable", ((ClassOrInterfaceType)intersectionType.getElements().get(0)).getNameAsString());
        assertTrue(intersectionType.getElements().get(1) instanceof ClassOrInterfaceType);
        assertEquals("Serializable", ((ClassOrInterfaceType)intersectionType.getElements().get(1)).getNameAsString());
    }
}

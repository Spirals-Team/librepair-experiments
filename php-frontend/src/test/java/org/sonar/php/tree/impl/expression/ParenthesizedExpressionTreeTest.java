/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.php.tree.impl.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.sonar.php.PHPTreeModelTest;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.ParenthesisedExpressionTree;

public class ParenthesizedExpressionTreeTest extends PHPTreeModelTest {

  @Test
  public void parenthesized_expression() throws Exception {
    ParenthesisedExpressionTree tree = parse("($a)", Kind.PARENTHESISED_EXPRESSION);

    assertThat(tree.is(Kind.PARENTHESISED_EXPRESSION)).isTrue();
    assertThat(tree.openParenthesis().text()).isEqualTo("(");
    assertThat(expressionToString(tree.expression())).isEqualTo("$a");
    assertThat(tree.closeParenthesis().text()).isEqualTo(")");
  }

  @Test
  public void parenthesized_yield_expression() throws Exception {
    ParenthesisedExpressionTree tree = parse("(yield $a)", Kind.PARENTHESISED_EXPRESSION);

    assertThat(tree.is(Kind.PARENTHESISED_EXPRESSION)).isTrue();
    assertThat(tree.openParenthesis().text()).isEqualTo("(");
    assertThat(expressionToString(tree.expression())).isEqualTo("yield $a");
    assertThat(tree.closeParenthesis().text()).isEqualTo(")");
  }

}

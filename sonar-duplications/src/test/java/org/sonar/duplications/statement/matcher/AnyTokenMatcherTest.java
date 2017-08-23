/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
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
package org.sonar.duplications.statement.matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sonar.duplications.token.Token;
import org.sonar.duplications.token.TokenQueue;

public class AnyTokenMatcherTest {

  @Test
  public void shouldMatch() {
    Token t1 = new Token("a", 1, 1);
    Token t2 = new Token("b", 2, 1);
    TokenQueue tokenQueue = spy(new TokenQueue(Arrays.asList(t1, t2)));
    List<Token> output = mock(List.class);
    AnyTokenMatcher matcher = new AnyTokenMatcher();

    assertThat(matcher.matchToken(tokenQueue, output), is(true));
    verify(tokenQueue).poll();
    verifyNoMoreInteractions(tokenQueue);
    verify(output).add(t1);
    verifyNoMoreInteractions(output);
  }

}

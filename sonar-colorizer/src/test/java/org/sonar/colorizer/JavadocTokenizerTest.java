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
package org.sonar.colorizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sonar.colorizer.SyntaxHighlighterTestingHarness.highlight;

public class JavadocTokenizerTest {

  JavadocTokenizer tokenizer = new JavadocTokenizer("<j>", "</j>");

  @Test
  public void testHighlighting() {
    assertThat(highlight("/**this is a javadoc*/ public ...", tokenizer)).isEqualTo("<j>/**this is a javadoc*/</j> public ...");
    assertThat(highlight("//this is not a javadoc", tokenizer)).isEqualTo("//this is not a javadoc");
  }

  @Test
  public void testHighlightingOnMultipleLines() {
    assertThat(highlight("/**this is \n a javadoc*/ private", tokenizer)).isEqualTo("<j>/**this is </j>\n<j> a javadoc*/</j> private");
  }
}

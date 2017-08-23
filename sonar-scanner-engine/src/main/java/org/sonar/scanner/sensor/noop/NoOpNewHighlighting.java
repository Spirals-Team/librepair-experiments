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
package org.sonar.scanner.sensor.noop;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class NoOpNewHighlighting implements NewHighlighting {
  @Override
  public void save() {
    // Do nothing
  }

  @Override
  public NoOpNewHighlighting onFile(InputFile inputFile) {
    // Do nothing
    return this;
  }

  @Override
  public NoOpNewHighlighting highlight(int startOffset, int endOffset, TypeOfText typeOfText) {
    // Do nothing
    return this;
  }

  @Override
  public NoOpNewHighlighting highlight(int startLine, int startLineOffset, int endLine, int endLineOffset, TypeOfText typeOfText) {
    // Do nothing
    return this;
  }

  @Override
  public NoOpNewHighlighting highlight(TextRange range, TypeOfText typeOfText) {
    // Do nothing
    return this;
  }
}
